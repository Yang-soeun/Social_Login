package Login.kakaoLogin.jwt.provider;

import Login.kakaoLogin.jwt.JwtProperties;
import Login.kakaoLogin.jwt.JwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.netty.util.internal.StringUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import reactor.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;


/**
 * JWT 토큰 생성 및 속성들의 정보를 판단하는 토큰 관련 기능
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    //토큰 생성
    public JwtToken createJwtToken(Long user_id, String nickname){
        //엑세스 토큰 생성
        String accessToken = JWT.create()
                .withSubject(String.valueOf(user_id))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("user_id", user_id)
                .withClaim("nickname", nickname)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        //리프레시 토큰 생성
        String refreshToken = JWT.create()
                .withSubject(String.valueOf(user_id) + "_refresh")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("user_id", user_id)
                .withClaim("nickname", nickname)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return new JwtToken(accessToken, refreshToken);
    }

    //엑세스 토큰만 생성
    public String createAccessToken(Long user_id, String nickname){
        return JWT.create()
                .withSubject(String.valueOf(user_id))
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("user_id", user_id)
                .withClaim("nickName", nickname)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    //JWT 엑세스 토큰이 헤더에 있는지 검증
    public String validAccessTokenHeader(HttpServletRequest request){
        String token = request.getHeader(JwtProperties.HEADER_STRING);

        if(!StringUtils.hasText(token))
            return null;
        return token;
    }

    public String getNickName(String token){
        return Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody().get("nickname").toString();
    }

    public Long getUserId(String token){
        return Long.valueOf(Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody().get("user_id").toString());
    }

    //토큰 만료시간 구하기
    public Long getExpiration(String accesstoken){
        Date expiration = Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET.getBytes()).build()
                .parseClaimsJws(accesstoken).getBody().getExpiration();

        long now = new Date().getTime();

        return expiration.getTime() - now;
    }

    //유효성 검사
//    public void validateToken(String token){
//        try{
//            Jwts.parserBuilder().setSigningKey(JwtProperties.SECRET.getBytes())
//                    .build().parseClaimsJws(token).getBody();
//        }catch (SignatureException e ){
//            throw new JwtValidException(e.getMessage());
//        }catch (ExpiredJwtException e) {
//            throw new JwtExpireException(e.getMessage());
//        } catch (MalformedJwtException e) {
//            throw new JwtValidException(e.getMessage());
//        }catch (IllegalArgumentException e) {
//            throw new JwtValidException(e.getMessage());
//        }catch (Exception e ){
//            throw new JwtValidException(e.getMessage());
//        }
//    }

}
