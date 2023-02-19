package Login.kakaoLogin.jwt.filter;

import Login.kakaoLogin.config.auth.PrincipalDetails;
import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.jwt.JwtProperties;
import Login.kakaoLogin.jwt.JwtToken;
import Login.kakaoLogin.jwt.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login으로 요청해서 username, password를 전송하면
// UsernamePasswordAuthenticationFilter가 동작을 함

public class JwtAuthenticatinFilter extends UsernamePasswordAuthenticationFilter {
    /**
     * AuthenticationManager를 통해서 로그인 시도를 하기 때문에 파라미터로 넘겨 받아야함
     */
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthenticatinFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // 로그인 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");


        try {
            //확인
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while((input = br.readLine()) !=null)
//                System.out.println(input);

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            //로그인 시도를 하기 위해서 토큰을 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getNickname(), user.getPassword());

            //토큰으로 로그인 시도
            /**
             * PrincipalDetailsService의 loadByUsername() 함수가 실행됨(패스워드는 스프링에서 알아서 처리해줌)
             * autenticationManager에 토큰을 넣어서 던지면 인증을 해줌
             * 인증이 되면 authentication을 받음 -> 여기에 로그인 한 정보가 담김
             * DB에 있는 username과 password가 일치
             */
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();//오브젝트를 리턴하기 때문에 다운캐스팅

            System.out.println("로그인 완료됨: " + principalDetails.getUser().getNickname());//출력이 된다는 것은 로그인이 되었다는 뜻

            //JWT 토큰을 사용하면서 세션을 만들 이유가 없지만
            //권한 관리를 Security가 대신 해주기 때문에 리턴함
            return authentication;//authentication 객체는 session 영역에 저장됨

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * attemptAuthentication 실행 후 인증이 정상적으로 되었으면 이 함수가 실행됨
     * 여기서 JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("인증완료: successfulAuthentication 실행");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();//이 정보를 통해서 JWT 토큰을 생성

        JwtToken jwtToken = jwtService.createAndSaveToken(principalDetails.getUser().getUser_id(),
                principalDetails.getUser().getNickname(), principalDetails.getUser().getRoles());

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken.getAccessToken());//사용자한테 응답
    }
}
