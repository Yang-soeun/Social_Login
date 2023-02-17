package Login.kakaoLogin.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Data
@NoArgsConstructor//기본 생성자를 생성 해줌: 초기값 세팅이 필요한 final 변수가 있을 경우 컴파일 에러가 발생함 주의!
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String password;

    //private Long user_id;//카카오 사용자 = 고유 id
    private String provider;
    private String nickname;
    private String email;
    private String profileImg;
    private LocalDateTime createTime;
    private String roles;//USER, MANAGER


    @Builder
    public User(String password, String roles, String nickname, String profileImg, String email, LocalDateTime createTime,String provider){
        this.password = password;
        this.roles = roles;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.email = email;
        this.createTime = createTime;
        this.provider = provider;
    }

    //사용자가 여러가지 권한을 가질 수 있음.
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }
}
