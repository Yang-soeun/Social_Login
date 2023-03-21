package google.Login.controller;

import google.Login.Repository.MemberRepository;
import google.Login.domain.User;
import google.Login.security.dto.UserAuthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class TestController {

    @Autowired private MemberRepository memberRepository;

    @Autowired private PasswordEncoder passwordEncoder;// 패스워드 암호화
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

//    @GetMapping("/test/member")
//    public void member(@AuthenticationPrincipal UserAuthDTO userAuthDTO){
//        log.info("=========================");
//    }

    @ResponseBody
    @GetMapping("/test/all")
    public String all() {
        return "all";
    }
    @ResponseBody
    @GetMapping("/test/member")
    public String member() {
        return "member";
    }

    @PostMapping("/join")
    public String join(User user){
        user.setRole("USER");

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        memberRepository.save(user);

        return "redirect:/loginForm";//회원가입이 정상적으로 완료가 되면 로그인 페이지로
    }
}
