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
        roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
        return roleHierarchyImpl;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/test/all").permitAll()
                .antMatchers("/test/member").hasRole("USER");
        //인가 인증 문제시 로그인 화면
        http.formLogin();
        http.csrf().disable();
        http.logout();
        //구글 oauth 인증 추가
        //UserLoginSuccessHandler 등록
        http.oauth2Login();//구글 oauth 인증 추가

        return http.build();
    }//end configure http
}//end class

