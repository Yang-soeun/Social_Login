package Login.kakaoLogin.controller;

import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.jwt.JsonResponse;
import Login.kakaoLogin.jwt.JwtToken;
import Login.kakaoLogin.jwt.service.JwtService;
import Login.kakaoLogin.profile.KakaoProfile;
import Login.kakaoLogin.service.KakaoService;
import Login.kakaoLogin.token.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final KakaoService kakaoService;
    private final JwtService jwtService;
    private final JsonResponse jsonResponse;

    /**
     * 카카오 로그인 test
     */
    @GetMapping("/login")
    public Map<String, String> kakaoLogin(@RequestParam("code") String code){
        //엑세스 토큰 받기
        KakaoToken kakaoToken = kakaoService.getAccessToken(code);

        //카카오 프로필 받기
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(kakaoToken.getAccess_token());

        //회원가입
        User saveUser = kakaoService.saveUser(kakaoProfile);

        //jwt 토큰 저장
        JwtToken result = jwtService.createAndSaveToken(saveUser.getUser_id(), saveUser.getNickname());

        return jsonResponse.successLoginResponse(result);
    }

}
