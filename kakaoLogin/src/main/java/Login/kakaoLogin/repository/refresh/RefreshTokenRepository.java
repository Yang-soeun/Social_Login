package Login.kakaoLogin.repository.refresh;

import Login.kakaoLogin.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    public Optional<RefreshToken> find(Long userId);//리프레시 토큰 조회

    public void save(RefreshToken refreshToken);//리프레시 토큰 저장

    public void delete(Long userId);//리프레시 토큰 삭제

}