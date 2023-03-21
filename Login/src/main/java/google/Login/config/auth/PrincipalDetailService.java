package google.Login.config.auth;


import google.Login.Repository.MemberRepository;
import google.Login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 시큐리티 설정에서 loginProcessingUrl("/login") 걸어놨기 때문에
 * /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행됨 -> 규칙
 */

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired private MemberRepository memberRepository;

    //
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = memberRepository.findByUsername(username);

        if(userEntity != null){//있는 경우
            return new PrincipalDetails(userEntity);//리턴된 값이 Authentication 내부에 들어가고
            //세션에 Authentication이 들어감
            //로그인 완료
        }

        return null;//없는 경우
    }
}
