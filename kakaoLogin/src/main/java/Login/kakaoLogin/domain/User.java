package Login.kakaoLogin.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity @Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;

    private String userId;//카카오 사용자 = 고유 id
    private String provider;
    private String nickname;
    private String email;
    private String profileImg;
    private LocalDateTime createTime;

    @Builder
    public User(String userId, String password, String nickname, String profileImg, String email, LocalDateTime createTime,String provider){
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.email = email;
        this.createTime = createTime;
        this.provider = provider;
    }
}
