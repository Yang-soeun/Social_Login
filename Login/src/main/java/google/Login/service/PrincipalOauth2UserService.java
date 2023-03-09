package google.Login.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수

    /**
     * 구글 로그인 후 코드 X, 엑세스 토큰 + 사용자 프로필 정보가 userRequest에 return 됨
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("-----loadUser-----");
        log.info("userRequest" + userRequest);
        System.out.println("getClientRegistration: "+userRequest.getClientRegistration());
        System.out.println("getAccessToken: "+ userRequest.getAccessToken());
        System.out.println("getAttributes: "+super.loadUser(userRequest).getAttributes());

        return super.loadUser(userRequest);
    }
}
