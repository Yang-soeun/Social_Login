package Login.kakaoLogin.jwt.provider;

public interface AuthentocationTokenProvider {
    boolean validateToken(String token);
}
