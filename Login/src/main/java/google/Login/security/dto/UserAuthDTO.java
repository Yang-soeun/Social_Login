package google.Login.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class UserAuthDTO extends User implements OAuth2User {

    private String email;
    private String name;
    private String passwrod;
    private Map<String, Object> OA2attr;

    //PrincipalOauth2UserService용 구성자
    public UserAuthDTO(
            String username,
            String password,
            List<GrantedAuthority> authorities,
            Map<String, Object> OA2attr){

        /**
         * this() 같은 클래스의 다른 생성자를 호출할 때 사용
         */
        this(username, password, authorities);
        this.OA2attr = OA2attr;
    }

    //구성자 설정
    public UserAuthDTO(String username, String password,
                       List<GrantedAuthority> authorities) {
        /**
         * super() 자식 생성자 안에서 부모 클래스의 생성자를 호출할때 사용
         */
        super(username, password, authorities);
        this.email = username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return OA2attr;
    }
}
