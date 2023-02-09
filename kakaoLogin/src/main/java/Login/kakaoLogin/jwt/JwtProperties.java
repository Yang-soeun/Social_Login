package Login.kakaoLogin.jwt;

public interface JwtProperties {
    String SECRET = "soeun";//JWT 토큰을 hash할때 사용할 비밀키
    int EXPIRATION_TIME = 864000000; //10 days, Token의 validation 기간
    String TOKEN_PREFIX = "Bearer ";//JWT Token의 prefix는 Bearer이다.
    String HEADER_STRING = "Authorization";//JWT Token은 Authorization header로 전달.
}
