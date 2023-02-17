package Login.kakaoLogin.jwt.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login으로 요청해서 username, password를 전송하면
// UsernamePasswordAuthenticationFilter가 동작을 함

/**
 * 소셜 로그인 할떄는 필요없다잉
 * 나중에 이어서 구현하기!!!!
 */
@RequiredArgsConstructor
public class JwtAuthenticatinFilter extends UsernamePasswordAuthenticationFilter {
    /**
     *AuthenticationManager를 통해서 로그인 시도를 하기 때문에 파라미터로 넘겨 받아야함
     */
    private final AuthenticationManager authenticationManager;

    // 로그인 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");
        return super.attemptAuthentication(request, response);
    }
}
