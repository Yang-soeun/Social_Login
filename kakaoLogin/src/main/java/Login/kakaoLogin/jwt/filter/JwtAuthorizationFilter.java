package Login.kakaoLogin.jwt.filter;

import Login.kakaoLogin.config.auth.PrincipalDetails;
import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.jwt.JwtProperties;
import Login.kakaoLogin.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 권한이나 인증이 필요한 특정 주소를 요청했을때 BasicAuthenticationFilter 라는 것을 타게 됨
 * 권한이나 인증이 필요한 주소가 아니라면 타지 않음
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    //인증이나 권한이 필요한 요청이 있을때 해당 필터를 타게 됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("인증이나 권한이 필요한 주소가 요청 됨");

        //header 값을 확인
        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader: " + jwtHeader);

        //헤더가 있는지 확인
       if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            //chain.doFilter(request, response);
            return;
        }

        //JWT 토큰검증
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");// Bearer 제외

        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build()
                .verify(jwtToken).getClaim("nickname").asString();//서명

        //username이 널이 아니면 서명이 정상적으로 됨
        if(username != null){
            User findUser = userRepository.findByNickname(username);

            PrincipalDetails principalDetails = new PrincipalDetails(findUser);

            //JWT 토큰 서명을 통해서 정상이면 Authenticaiotn 객체를 만들어준다.
            //username이 널이 아니므로 정상적으로 사용자가 인증이 되었기 때문에
            //임의로 Authentication 객체를 생성 가능
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            //강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
   }
}
