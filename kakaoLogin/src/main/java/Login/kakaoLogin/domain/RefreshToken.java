package Login.kakaoLogin.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    private String refreshToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
