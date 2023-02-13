package Login.kakaoLogin.controller;

import Login.kakaoLogin.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {
    private final JwtService jwtService;
}
