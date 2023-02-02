package sociallogin.kakao.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoAPI {

    /**
     * 토큰 받기
     * POST /oauth/token
     * Host: kauth.kakao.com
     */
    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";//새로운 access token을 재발급 받을 수 있는 유호 기간이 긴 token
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            //Parameter
            sb.append("grant_type=authorization_code");//이걸로 고정
            sb.append("&client_id=8b8cede7921920490681a4cfd0c75f2a");//앱 REST API 키
            sb.append("&redirect_uri=http://localhost:8080/login");//인가 코드가 리다이렉트된 URI
            sb.append("&code="+code);//인가 코드 받기 요청으로 얻은 인가코드

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("response code = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine())!=null) {
                result += line;
            }
            System.out.println("response body="+result);

//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);

            JsonObject element = new Gson().fromJson(result, JsonObject.class);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }


    /**
     * 사용자 정보 받기
     * 현재 로그인한 사용자의 정보를 불러옴
     * 이 API를 사용하려면 동의 항목 설정을 참고하여 각 응답 필드에 필요한 동의 항목을 설정해야함.
     * 동의 항목이 설정되어 있더라도 사용자가 동의하지 않으면 사용자 정보를 받을 수 없다.
     */
    public HashMap<String, Object> getUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        //Host: kapi.kakao.cpm
        //GET/POST /v2/user/me
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //Authorization: Beaarer ${ACCESS_TOKEN} 사용자 인증 수단, 엑세스 토큰 값
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode =" + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body ="+result);

//            JsonParser parser = new JsonParser();
//            JsonElement element =  parser.parse(result);

            JsonObject element = new Gson().fromJson(result, JsonObject.class);

            //property_keys 파라미터를 사용하면 특정 정보만 지정 가능
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();//properties.nickname
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();//kakao_account.email

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public void kakaoLogout(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while((line = br.readLine()) != null) {
                result+=line;
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
