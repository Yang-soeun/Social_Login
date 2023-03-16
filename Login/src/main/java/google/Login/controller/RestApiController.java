package google.Login.controller;

import google.Login.Repository.MemberRepository;
import google.Login.Repository.MemberRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestApiController {

    @Autowired private MemberRepository memberRepository;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication){
        System.out.println("/test/login ========================");
        System.out.println("authentication: " + authentication.getPrincipal());

        return "세션 정보 확인하기";
    }
}
