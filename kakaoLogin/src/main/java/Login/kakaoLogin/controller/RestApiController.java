package Login.kakaoLogin.controller;

import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.jwt.JsonResponse;
import Login.kakaoLogin.jwt.JwtToken;
import Login.kakaoLogin.jwt.service.JwtService;
import Login.kakaoLogin.profile.KakaoProfile;
import Login.kakaoLogin.repository.UserRepository;
import Login.kakaoLogin.service.KakaoService;
import Login.kakaoLogin.token.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class RestApiController {
    private final KakaoService kakaoService;
    private final JwtService jwtService;
    private final JsonResponse jsonResponse;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 회원가입
     */

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(@ModelAttribute User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("USER");
        user.setCreateTime(LocalDateTime.now());
        userRepository.save(user);

        return "회원가입 완료";
    }

    /**
     * 권한 테스트
     */
    //USER, MANAGER 권한 접근 가능
    @GetMapping("/test/user")
    @ResponseBody
    public String user(){
        return "user";
    }

    //MANAGER 권한만 접근 가능
    @GetMapping("/test/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    /**
     * 카카오 로그인 test
     */
    @GetMapping("/login/kakao")
    public Map<String, String> kakaoLogin(@RequestParam("code") String code){
        //엑세스 토큰 받기
        KakaoToken kakaoToken = kakaoService.getAccessToken(code);

        //카카오 프로필 받기
        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(kakaoToken.getAccess_token());

        //회원가입
        User saveUser = kakaoService.saveUser(kakaoProfile);

        //jwt 토큰 저장
        JwtToken result = jwtService.createAndSaveToken(saveUser.getUser_id(), saveUser.getNickname(), saveUser.getRoles());

        return jsonResponse.successLoginResponse(result);
    }
}
