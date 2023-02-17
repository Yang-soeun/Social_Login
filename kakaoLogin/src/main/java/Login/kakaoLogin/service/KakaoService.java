package Login.kakaoLogin.service;

import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.profile.KakaoProfile;
import Login.kakaoLogin.repository.UserRepository;
import Login.kakaoLogin.token.KakaoToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserRepository userRepository;

    //엑세스 토큰을 받는 함수
    public KakaoToken getAccessToken(String code){
        String reqURL = "https://kauth.kakao.com/oauth/token";
        String client_id = "8b8cede7921920490681a4cfd0c75f2a";
        String redirect_uri = "http://localhost:8080/login";
        String client_secret = "YhbCEZ2ulDUKkCStKjtzCXjcKlIQvXpT";

        //Request Parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");//authorization_code로 고정
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        params.add("client_secret", client_secret);

        //요청
        WebClient wc = WebClient.create(reqURL);//base url을 지정해서 WebClient 생성
        //HTTP 요청 보내기: Post 요청
        String response = wc.post()
                .uri(reqURL)
                .body(BodyInserters.fromFormData(params))
                .header("Content-type","application/x-www-form-urlencoded;charset=utf-8" )
                .retrieve()//HTTP 요청을 직접 수행하고 응답 본문을 검색
                .bodyToMono(String.class)//body 타입
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoToken kakaoToken = null;

        try{
            kakaoToken = objectMapper.readValue(response, KakaoToken.class);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return kakaoToken;
    }

    //사용자 정보 가져오기
    public KakaoProfile getKakaoProfile(String token){
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        WebClient wc = WebClient.create(reqUrl);
        String response = wc.post()
                .uri(reqUrl)
                .header("Authorization", "Bearer " + token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try{
            kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    //저장
    @Transactional
    public User saveUser(KakaoProfile profile){
        User user = userRepository.findByUseremail(profile.getKakao_account().getEmail());

        //처음 사용자 강제 회원가입
        /**
         * userid, password, nickname, profileImg
         * email, createTime, provider
         */
        if(user == null){
            user = User.builder()
                    .password(null)
                    .roles("USER")
                    .nickname(profile.getKakao_account().getProfile().getNickname())
                    .profileImg(profile.getKakao_account().getProfile().getProfile_image_url())
                    .email(profile.getKakao_account().getEmail())
                    .createTime(LocalDateTime.now())
                    .provider("Kakao")
                    .build();

            userRepository.save(user);

        }

        return user;
    }
}
