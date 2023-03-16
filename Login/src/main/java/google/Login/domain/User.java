package google.Login.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    private String provider;//google
    private String providerId;

    @OneToOne(mappedBy = "user")
    private UserRole userRole;
}
