package Login.kakaoLogin.repository;

import Login.kakaoLogin.domain.RefreshToken;
import Login.kakaoLogin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository{
    public User findByUseremail(String email);

    public Optional<User> findByUserId(Long userId);

    public User findUserById(Long userId);

    public void save(User user);
}
