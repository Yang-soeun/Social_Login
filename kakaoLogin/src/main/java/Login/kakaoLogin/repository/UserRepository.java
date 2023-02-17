package Login.kakaoLogin.repository;

import Login.kakaoLogin.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository{
    User findByUseremail(String email);

    Optional<User> findByUserId(Long userId);

    User findUserById(Long userId);

    void save(User user);

    User findByNickname(String username);
}
