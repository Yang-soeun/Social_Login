package google.Login.service;

import google.Login.Repository.MemberRepository;
import google.Login.domain.Role_set;
import google.Login.domain.User;
import google.Login.security.dto.UserAuthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * PrincipalOauth2UserService의 loadUser()의 경우 일반적인 로그인과 다르게 OAuth2User 타입 반환
 * -> 일반적으로 UserDetails, TestController는 UserAuthDTO 타입 반환 화면에서 정상적으로 회원 정보를 처리하기 위해서는
 * OAuth2User -> UserAuthDTO 타입으로 변환 필요
 */


@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired private MemberRepository memberRepository; //db 저장을 위해

    @Autowired private PasswordEncoder passwordEncoder;// 패스워드 암호화

    public static  String provider;
    public static String providerId;


    //알아낸 email로 데이터베이스에 추가하는 작업
    private User saveSocialMember(String email) throws SQLException{
        log.info("saveSocialMember 시작");

        //기존에 동일한 이메일로 가입한 회원인지 확인
        User result = memberRepository.findByEmail(email);

        //기존 회원이면 정보 반환
        if(!(result == null)){
            log.info("기존 회원");
            return result;
        }

        //가입한적이 없다면
        //user 정보 저장
        LocalDateTime createTime = LocalDateTime.now();
        result = User.builder()
                .username(provider + "_" + providerId)
                .password(passwordEncoder.encode("security"))
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .role(Role_set.USER.toString())
                .createDate(createTime)
                .build();

        memberRepository.save(result);//db에 저장

        return result;//추가된 정보 반환
    }

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

        log.info("==========oAuth2User==============");
        oAuth2User.getAttributes().forEach((k, v) ->{
            log.info(k + ":" + v);
        });

        provider = userRequest.getClientRegistration().getRegistrationId();
        providerId = oAuth2User.getAttribute("sub");

        //신규 회원 테이블에 저장
        String email = null;

        if(clientName .equals("Google"))//구글 인증 확인
            email = oAuth2User.getAttribute("email");

        log.info("구글 인증 확인");
        log.info("email : " + email);

        try{
            User user = saveSocialMember(email);
            log.info("---saveSocialMember 완료---");

            //UserAuthDTO 생서이 필요한 aithrities
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(
                    new SimpleGrantedAuthority(user.getRole()));

            //OAuth2User를 UserAuthDTO로 변환
            UserAuthDTO userAuthDTO =
                    new UserAuthDTO(user.getEmail(),
                            user.getPassword(),
                            authorities,
                            oAuth2User.getAttributes());
            userAuthDTO.setName(user.getUsername());
            userAuthDTO.setPasswrod(user.getPassword());

            return userAuthDTO;

        } catch (SQLException e){
            log.info("saveSocialMember error");
            log.info(e.toString());

            return null;
        }
    }
}
