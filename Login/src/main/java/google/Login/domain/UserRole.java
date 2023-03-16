package google.Login.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserRole {

    @Id
    @Column(insertable = false, updatable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role_set role_set;

    @OneToOne
    @JoinColumn(name = "email")
    private User user;
}
