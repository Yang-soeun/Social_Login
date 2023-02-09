package Login.kakaoLogin.jwt;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JsonResponse {

    //로그인시 응답
    public Map<String, String> successLoginResponse(JwtToken jwtToken){
        Map<String, String> response = new LinkedHashMap<>();

        response.put("status", "200");
        response.put("message", "accessToken, refreshToken이 생성되었습니다.");
        response.put("accessToken", jwtToken.getAccessToken());
        response.put("refreshToken", jwtToken.getRefreshToken());

        return response;
    }

    //인증 요구
    public Map<String, String> requiredJwtTokenResponse() {
        Map<String ,String> response = new LinkedHashMap<>();

        response.put("status", "401");
        response.put("message", "인증이 필요한 페이지 입니다. 로그인을 해주세요");

        return response;
    }

    //accessToken이 만료된 경우
    public Map<String, String> requiredRefreshTokenResponse() {
        Map<String, String> response = new LinkedHashMap<>();

        response.put("status", "401");
        response.put("message", "accessToken이 만료되었거나 잘못된 값입니다.");

        return response;
    }

    //refresh 토큰 재발급 response
    public Map<String, String> recreateTokenResponse(JwtToken jwtToken) {
        Map<String ,String > response = new LinkedHashMap<>();

        response.put("status", "200");
        response.put("message", "refresh, access 토큰이 재발급되었습니다.");
        response.put("accessToken", jwtToken.getAccessToken());
        response.put("refreshToken", jwtToken.getRefreshToken());

        return response;
    }
}
