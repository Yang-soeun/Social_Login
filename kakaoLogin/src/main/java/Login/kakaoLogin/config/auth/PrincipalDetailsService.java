package Login.kakaoLogin.config.auth;

import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//로그인 요청이 올떄 동작을 함
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("동작함");

        User findUSer = userRepository.findByNickname(username);

        if(findUSer == null){
            throw new UsernameNotFoundException(username);
        }

        return new PrincipalDetails(findUSer);
    }
}
