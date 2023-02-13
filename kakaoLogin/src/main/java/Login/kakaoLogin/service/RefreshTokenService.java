package Login.kakaoLogin.service;

import Login.kakaoLogin.domain.RefreshToken;
import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.repository.UserRepository;
import Login.kakaoLogin.repository.refresh.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.standard.expression.NotEqualsExpression;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    //리프레시 토큰을 가져오는 메서드
    public Optional<RefreshToken> findRefreshToken(Long userId){
        return refreshTokenRepository.find(userId);
    }

    //리프레시 토큰 저장 메서드
    @Transactional
    public RefreshToken saveRefreshToken(Long userId, RefreshToken refreshToken){
        Optional<User> findUser =  userRepository.findByUserId(userId);
        refreshToken.setUser(findUser.get());
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    //리프레시 토큰 업데이트
    @Transactional
    public RefreshToken updateRefreshToken(Long userId, String refreshToken){
        Optional<RefreshToken> findToken = refreshTokenRepository.find(userId);
        findToken.get().setRefreshToken(refreshToken);

        return findToken.get();
    }

    //사용자의 refreshToken과 일치하는지 체크하는 메서드
    //재발급할때 사용
    public String validRefreshToken(Long userId, String refreshToken){
        String findRefreshToken = findRefreshToken(userId).get().getRefreshToken();

        //토큰값이 잘못된 경우
        if(!findRefreshToken.equals(refreshToken)){
            log.error("리프레시 토큰이 잘못된 값입니다.");
        }

        return refreshToken;
    }
}
