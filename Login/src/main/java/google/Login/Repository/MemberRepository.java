package google.Login.Repository;

import google.Login.domain.User;
import google.Login.domain.UserRole;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {
    User findByEmail(String email);

    void save(User user);

    void saveRole(UserRole userRole);
}
