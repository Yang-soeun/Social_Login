package Login.kakaoLogin.config;
import Login.kakaoLogin.jwt.filter.JwtAuthenticatinFilter;
import Login.kakaoLogin.jwt.filter.JwtAuthorizationFilter;
import Login.kakaoLogin.jwt.service.JwtService;
import Login.kakaoLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .antMatchers(HttpMethod.GET,"/join", "/joinForm")//일반 로그인
                .antMatchers("/login/kakao");//카카오 로그읜
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                  //JWT는 기본적으로 session을 사용하지 않기때문에 STATELESS 무상태 유지.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //formLogin 방식 사용안함, json 방식으로 전달
                .formLogin().disable()
                .httpBasic().disable() //Bearer 방식 사용
                .logout().disable()
                .apply(new MyCustomDsl())
                .and()
                .authorizeRequests()
                .antMatchers("/test/user").hasAuthority("USER")
                .antMatchers("/test/manager").hasAuthority("MANAGER")
                .and()
                .build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity>{
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            http
                    .addFilter(corsConfig.corsFilter())
                    .addFilter(new JwtAuthenticatinFilter(authenticationManager, jwtService))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
        }
    }
}
