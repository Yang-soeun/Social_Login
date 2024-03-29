package google.Login.config;

import google.Login.security.handler.UserLoginSuccessHandler;
import google.Login.service.PrincipalOauth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig{

//    @Autowired
//    private PrincipalOauth2UserService principalOauth2UserService;

    //UserLoginSuccessHandler bean으로 등록
//    @Bean
//    public UserLoginSuccessHandler successHandler(){
//        return new UserLoginSuccessHandler();
//    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchyImpl(){
        log.info("실행");
        RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
        roleHierarchyImpl.setHierarchy("ADMIN > MANAGER > USER");
        return roleHierarchyImpl;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/test/all").authenticated()
                .antMatchers("/test/member").hasAnyAuthority("USER")
                .anyRequest().permitAll()

                .and()
                //인가 인증 문제시 로그인 화면
                .formLogin()
                .loginPage("/loginForm")//사용자 정의 로그인 페이지, 미인증 사용자가 접근하면 이 페이지로 돌아옴
                .loginProcessingUrl("/login")//login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌 -> controller 안만들어도 됨
                .defaultSuccessUrl("/")//로그인 성공 후 이동 페이지
                .and()
                //구글 oauth 인증 추가
                //UserLoginSuccessHandler 등록
                .oauth2Login()//구글 oauth 인증 추가
                .loginPage("/loginForm");

        return http.build();
    }//end configure http
}//end class

