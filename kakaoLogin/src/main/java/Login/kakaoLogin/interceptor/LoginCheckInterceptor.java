package Login.kakaoLogin.interceptor;

import Login.kakaoLogin.jwt.provider.JwtProvider;
import Login.kakaoLogin.repository.UserRepository;
import Login.kakaoLogin.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("로그인 인터셉터 실행{}", requestURI);

        String id = (String)request.getSession().getAttribute("user_id");

        if(id == null){
            log.info("미인증 사용자 요청");
            response.sendRedirect("/?redirect=" + requestURI);
            return false;
        }
        return true;
    }
}
