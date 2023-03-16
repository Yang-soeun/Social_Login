package google.Login.service;

import google.Login.Repository.MemberRepository;
import google.Login.Repository.MemberRepositoryImpl;
import google.Login.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired private MemberRepository memberRepository; //db 저장을 위해
    @Autowired private PasswordEncoder passwordEncoder;// 패스워드 암호화

    //알아낸 email로 데이터베이스에 추가하는 작업
//    private User saveSocialMember(String email) throws SQLException{
//        log.info("saveSocialMember 시작");
//
//        //기존에 동일한 이메일로 가입한 회원인지 확인
//        User result = memberRepository.findByEmail(email);
//
//        //기존 회원이면 정보 반환
//        if(!(result == null)){
//            log.info("기존 회원");
//            return result;
//        }
//
//        //가입한적이 없다면
//
//    }

    //구글로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수

    /**
     * 구글 로그인 후 코드 X, 엑세스 토큰 + 사용자 프로필 정보가 userRequest에 return 됨
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("-----loadUser-----");
        log.info("userRequest" + userRequest);
        System.out.println("getClientRegistration: "+userRequest.getClientRegistration());//registrationId로 어떤 OAuth로 로그인 했는지 확인가능
        System.out.println("getAccessToken: "+ userRequest.getAccessToken().getTokenValue());

        /**
         * 구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-Client 라이브러리가) -> code를 통해서 AccessToken을 요청 -> AccessToken 받음 여기까지가 userRequest 정보
         * userRequest 정보 -> 회원프로필 받아야함(이때 loadUser 함수 사용) -> 구글로부터 회원 프로필 받음
         */
        //System.out.println("getAttributes: "+super.loadUser(userRequest).getAttributes());

        String clientName = userRequest.getClientRegistration().getClientName();
        //인증 제공자 출력
        log.info("clientName" + clientName);

        //사용자 정보 가져오기 구글에서 허용한 API 범위
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("=====================================");
        oAuth2User.getAttributes().forEach((k, v) ->{
            log.info(k + ":" + v);
        });

        return super.loadUser(userRequest);
    }
}
