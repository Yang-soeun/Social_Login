# Social_Login
### 🟡 카카오 로그인
<details>
<summary> ✏ dependencies </summary>
<div markdown="1">

#### gson - JAVA 객체를 JSON 데이터로 손쉽게 변환할 수 있고, 그 반대도 가능하게 해줌

```
dependencies {
  implementation 'com.google.code.gson:gson:2.9.0'
}
```

#### JWT

```
dependencies {
	implementation group: 'com.auth0', name: 'java-jwt', version: '3.10.2'
	implementation 'io.jsonwebtoken:jjwt-api:0.11.2'
	implementation 'io.jsonwebtoken:jjwt-jackson:0.11.2'
	runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.2'
}
```

#### WebClient
```
implementation 'org.springframework.boot:spring-boot-starter-webflux'
```

</div>
</details>

<details>
<summary> ✏ 애플리케이션 설정 </summary>
<div markdown="1">

#### 1️⃣ 애플리케이션 추가

![image](https://user-images.githubusercontent.com/87464750/216276191-354dd2ef-e71b-45da-8d7d-052c80e85273.png)

#### 2️⃣ 플랫폼 등록
##### 내 애플리케이션 -> 앱 설정 -> 플랫폼
![image](https://user-images.githubusercontent.com/87464750/216276640-0a57db3b-d5f9-419e-ae83-fff89947c151.png)

- 배포하면 배포한 주소도 추가해야함.

#### 3️⃣ Redirect URL 설정
##### 내 애플리케이션 -> 제품 설정 -> 카카오 로그인
##### 활성화 설정 ON
##### Redirect URI입력

</div>
</details>

<details>
<summary> ✏ URL 구조 </summary>
<div markdown="1">

![image](https://user-images.githubusercontent.com/87464750/216758177-ab16ba64-5934-4072-851c-f50e5a56a6bd.png)

</div>
</details>

<details>
<summary> ✏ AccessToken & RefreshToken </summary>
<div markdown="1">
 
 #### 1️⃣ AccessToken - 인증 처리 역할
 - 처음 로그인 요청시 서버에서 실제 유저의 정보가 담긴 AccessToken을 발행.
 - Client는 이 AccessToke을 저장한 후, 요청마다 AccessToken을 보내서 해당 AccessToken을 서버에서 검증 후 유효하면 요청에 맞는 응답을 진행.
 
 #### 2️⃣ RefreshToken - 재발급 역할
 - 처음 로그인 요청시 서버에서 AccessToken 재발급 용도인 RefreshToken을 발행.
 - 이때, 클라이언트는 RefreahToekn을 저장하지 않고 보통 서버 DB에 저장.
 - RefreshToken이 유효하면, AccessToken의 재발급을 진행.
 
</div>
</details>

<details>
<summary> ✏ WebClient </summary>
<div markdown="1">

#### WebClient란?
- HTTP 요청을 수행하기 위한 Non-Blocking 및 반응형 웹 클라이언트
##### ❕ RestTemplate와 차이점
- RestTemplate = Blocking 방식
- WebClient - Non-Blocking 방식

사용방법 - https://howtodoinjava.com/spring-webflux/webclient-get-post-example/

</div>
</details>

### REST API를 사용한 카카오 로그인 과정
#### 1️⃣ 인가 코드 받기
![image](https://user-images.githubusercontent.com/87464750/216277235-ea75aed7-770b-4da9-be25-92828d8eadd9.png)

- 서비스 서버가 카카오 인증 서버로 인가 코드 받기를 요청
- 카카오 인증 서버가 사용자에게 카카오 계정 로그인을 통한 인증을 요청
- 사용자가 카카오계정으로 로그인
- 카카오 인증 서버가 사용자에게 동의 화면을 출력하여 인가를 위한 사용자 동의를 요청
  - 동의항목 설정에 따라서 다름
- 사용자가 필수 동의 항목, 이 외 원하는 동의 항목에 동의한 뒤 계속하기 버튼 누름
- 카카오 인증 서버는 서비스 서버의 Redirect URI 인가 코드를 전달

#### 2️⃣ 토큰 받기
![image](https://user-images.githubusercontent.com/87464750/216277936-206bad5c-1d75-4f44-89fd-6a1f5cfb73f1.png)

- 서비스 서버가 Redirect URI로 전달받은 인가 코드로 토큰 받기를 요청
- 카카오 인증 서버가 토큰을 발급해 서비스 서버에 전달

#### 3️⃣ 사용자 로그인 처리
![image](https://user-images.githubusercontent.com/87464750/216278513-69b221aa-d9bd-405b-898e-72be65abb587.png)

- 서비스 서버가 발급받은 엑세스 토큰으로 사용자 정보 가져오기를 요청해 사용자의 회원번호 및 정보를 조회하여 서비스 회원인지 확인
- 서비스 회원 정보 확인 결과에 따라 서비스 로그인 또는 회원 가입 과정을 진행
- 이 외 서비스에서 필요한 로그인 절차를 수행한 후, 카카오 로그인한 사용자의 서비스 로그인 처리를 완료








