package google.Login.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    private String provider;//google
    private String providerId;
    private String role;
    private LocalDateTime createDate;

    @Builder
    public User(String username, String password, String email, String provider, String providerId, String role, LocalDateTime createDate){
        this.username = username;
        this.password = password;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.createDate = createDate;
    }
}
