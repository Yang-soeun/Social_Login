package Login.kakaoLogin.controller;

import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.profile.KakaoProfile;
import Login.kakaoLogin.repository.UserRepository;
import Login.kakaoLogin.service.KakaoService;
import Login.kakaoLogin.token.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserRepository userRepository;
    private final KakaoService kakaoService;

    @GetMapping("/login")
    public Map<String, String> kakaoLogin(@RequestParam("code") String code){
        //엑세스 토큰 받기
        KakaoToken kakaoToken = kakaoService.getAccessToken(code);

        //카카오 프로필 받기
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(kakaoToken.getAccess_token());

        //회원가입
        User saveUser = kakaoService.saveUser(kakaoProfile);

    }
}
