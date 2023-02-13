package Login.kakaoLogin.repository.refresh;

import Login.kakaoLogin.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    //리프레시 토큰 조회
    Optional<RefreshToken> find(Long userId);

    //리프레시 토큰 저장
    void save(RefreshToken refreshToken);

    //리프레시 토큰 삭제
    void delete(Long userId);
}
