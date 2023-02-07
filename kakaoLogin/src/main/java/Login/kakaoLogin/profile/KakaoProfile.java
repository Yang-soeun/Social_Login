package Login.kakaoLogin.profile;

import lombok.Data;

/**
 * Kakao Developers에 자세히 나와있음
 */
@Data
public class KakaoProfile {

    //Response
    public String id;//회원번호(User에 userid에 저장하기 위해서 Long -> String으로)
    public String connected_at;//서비스에 연결 완료된 시각(UTC)
    public KakaoAccount kakao_account;//카카오계정 정보
    public Properties properties;

    @Data
    public class KakaoAccount {
        //사용자 동의 시 닉네임 제공 가능
        public Boolean profile_nickname_needs_agreement;//닉네임
        //사용자 동의 시 프로필 사진 제공 가능
        public Boolean profile_image_needs_agreement;//프로필
        public Profile profile;// JSON 프로필 정보
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;


        @Data
        public class Profile{
            public String nickname;
            //프로필 정보 동의항목 필요
            public String thumbnail_image_url;//프로필 미리보기 이미지 URL
            public String profile_image_url;//프로필 사진 URL
            public Boolean is_default_image;//사용자가 등록한 프로필 사진이 없을 경우, 기본 프로필 사진 제공
        }

    }

    public class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

}
