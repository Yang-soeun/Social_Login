package Login.kakaoLogin.token;

import lombok.Data;

/**
 * Kakao Developers에 자세히 나와있음
 * Response
 */
@Data
public class KakaoToken {
    private String token_type;//토큰 타입, bearer로 고정
    private String access_token;//사용자 엑세스 토큰 값
    private int expires_in;//엑세스 토큰과 ID 토큰의 만료 시간(초), 둘의 만료 시간은 동일
    private String refresh_token;//사용자 리프레시 토큰 값
    /**
     * scope 안하니깐 오류 생기더라
     * 인증된 사용자의 정보 조회 권한 범위
     */
    private String scope;
    private int refresh_token_expires_in;//리프레시 토큰 만료 시간(초)
}
