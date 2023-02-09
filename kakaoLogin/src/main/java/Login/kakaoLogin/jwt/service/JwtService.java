package Login.kakaoLogin.jwt.service;

import Login.kakaoLogin.domain.RefreshToken;
import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.jwt.JwtToken;
import Login.kakaoLogin.jwt.provider.JwtProvider;
import Login.kakaoLogin.service.RefreshTokenService;
import Login.kakaoLogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    //토큰 생성 및 저장
    public JwtToken createAndSaveToken(Long user_id, String nickname){
        JwtToken createToken = jwtProvider.createJwtToken(user_id, nickname); //토큰 생성

        //리프레시 토큰이 있는 사용자인지 판별
        Optional<RefreshToken> findRefreshToken = refreshTokenService.findRefreshToken(user_id);

        //리프레시 토큰이 없는 경우: 회원가입하고 처음 로그인 하는 사용자, 로그아웃 한 사용자
        if(findRefreshToken.isEmpty()){
            RefreshToken refreshToken = new RefreshToken(createToken.getRefreshToken());
            refreshTokenService.saveRefreshToken(user_id, refreshToken);
        }else{//리프레시 토큰이 있는 경우: 기존 이용자가 업데이트 하는 경우 사용
            refreshTokenService.updateRefreshToken(user_id, createToken.getRefreshToken());
        }

        return createToken;
    }

    //리프레시 토큰 정보를 가지고 멕세스 토큰, 리프레시 토큰 재발급
    public JwtToken recreateToken(String refreshToken){
        //리프레시 토큰에서 user_id 추출
        Long findId = jwtProvider.getUserId(refreshToken);

        //User 찾기
        User findUser = userService.findById(findId);

        //엑세스 토큰 재생성
        String accessToken = jwtProvider.createAccessToken(findId, findUser.getNickname());

        return new JwtToken(accessToken, refreshToken);
    }

    //Token에서 user 찾는 메서드: 회원을 수정할때 사용
    @Transactional(readOnly = true)
    public User findUserInToken(String token){
        Long findUserId = jwtProvider.getUserId(token);

        return userService.findById(findUserId);
    }
}
