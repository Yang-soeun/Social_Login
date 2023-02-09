package Login.kakaoLogin.service;

import Login.kakaoLogin.domain.User;
import Login.kakaoLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long user_id){
        User findUser = userRepository.findUserById(user_id);

        return findUser;
    }
}
