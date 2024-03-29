package google.Login.Repository;

import google.Login.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository{
    User findByEmail(String email);

    void save(User user);

    User findByUsername(String username);
}
