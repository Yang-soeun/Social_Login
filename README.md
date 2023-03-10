# Social_Login
### ๐ก ์นด์นด์ค ๋ก๊ทธ์ธ
<details>
<summary> โ dependencies </summary>
<div markdown="1">

#### gson - JAVA ๊ฐ์ฒด๋ฅผ JSON ๋ฐ์ดํฐ๋ก ์์ฝ๊ฒ ๋ณํํ  ์ ์๊ณ , ๊ทธ ๋ฐ๋๋ ๊ฐ๋ฅํ๊ฒ ํด์ค

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
<summary> โ ์ ํ๋ฆฌ์ผ์ด์ ์ค์  </summary>
<div markdown="1">

#### 1๏ธโฃ ์ ํ๋ฆฌ์ผ์ด์ ์ถ๊ฐ

![image](https://user-images.githubusercontent.com/87464750/216276191-354dd2ef-e71b-45da-8d7d-052c80e85273.png)

#### 2๏ธโฃ ํ๋ซํผ ๋ฑ๋ก
##### ๋ด ์ ํ๋ฆฌ์ผ์ด์ -> ์ฑ ์ค์  -> ํ๋ซํผ
![image](https://user-images.githubusercontent.com/87464750/216276640-0a57db3b-d5f9-419e-ae83-fff89947c151.png)

- ๋ฐฐํฌํ๋ฉด ๋ฐฐํฌํ ์ฃผ์๋ ์ถ๊ฐํด์ผํจ.

#### 3๏ธโฃ Redirect URL ์ค์ 
##### ๋ด ์ ํ๋ฆฌ์ผ์ด์ -> ์ ํ ์ค์  -> ์นด์นด์ค ๋ก๊ทธ์ธ
##### ํ์ฑํ ์ค์  ON
##### Redirect URI์๋ ฅ

</div>
</details>

<details>
<summary> โ URL ๊ตฌ์กฐ </summary>
<div markdown="1">

![image](https://user-images.githubusercontent.com/87464750/216758177-ab16ba64-5934-4072-851c-f50e5a56a6bd.png)

</div>
</details>

<details>
<summary> โ AccessToken & RefreshToken </summary>
<div markdown="1">
 
 #### 1๏ธโฃ AccessToken - ์ธ์ฆ ์ฒ๋ฆฌ ์ญํ 
 - ์ฒ์ ๋ก๊ทธ์ธ ์์ฒญ์ ์๋ฒ์์ ์ค์  ์ ์ ์ ์ ๋ณด๊ฐ ๋ด๊ธด AccessToken์ ๋ฐํ.
 - Client๋ ์ด AccessToke์ ์ ์ฅํ ํ, ์์ฒญ๋ง๋ค AccessToken์ ๋ณด๋ด์ ํด๋น AccessToken์ ์๋ฒ์์ ๊ฒ์ฆ ํ ์ ํจํ๋ฉด ์์ฒญ์ ๋ง๋ ์๋ต์ ์งํ.
 
 #### 2๏ธโฃ RefreshToken - ์ฌ๋ฐ๊ธ ์ญํ 
 - ์ฒ์ ๋ก๊ทธ์ธ ์์ฒญ์ ์๋ฒ์์ AccessToken ์ฌ๋ฐ๊ธ ์ฉ๋์ธ RefreshToken์ ๋ฐํ.
 - ์ด๋, ํด๋ผ์ด์ธํธ๋ RefreahToekn์ ์ ์ฅํ์ง ์๊ณ  ๋ณดํต ์๋ฒ DB์ ์ ์ฅ.
 - RefreshToken์ด ์ ํจํ๋ฉด, AccessToken์ ์ฌ๋ฐ๊ธ์ ์งํ.
 
</div>
</details>

<details>
<summary> โ WebClient </summary>
<div markdown="1">

#### WebClient๋?
- HTTP ์์ฒญ์ ์ํํ๊ธฐ ์ํ Non-Blocking ๋ฐ ๋ฐ์ํ ์น ํด๋ผ์ด์ธํธ
##### โ RestTemplate์ ์ฐจ์ด์ 
- RestTemplate = Blocking ๋ฐฉ์
- WebClient - Non-Blocking ๋ฐฉ์

์ฌ์ฉ๋ฐฉ๋ฒ - https://howtodoinjava.com/spring-webflux/webclient-get-post-example/

</div>
</details>

### REST API๋ฅผ ์ฌ์ฉํ ์นด์นด์ค ๋ก๊ทธ์ธ ๊ณผ์ 
#### 1๏ธโฃ ์ธ๊ฐ ์ฝ๋ ๋ฐ๊ธฐ
![image](https://user-images.githubusercontent.com/87464750/216277235-ea75aed7-770b-4da9-be25-92828d8eadd9.png)

- ์๋น์ค ์๋ฒ๊ฐ ์นด์นด์ค ์ธ์ฆ ์๋ฒ๋ก ์ธ๊ฐ ์ฝ๋ ๋ฐ๊ธฐ๋ฅผ ์์ฒญ
- ์นด์นด์ค ์ธ์ฆ ์๋ฒ๊ฐ ์ฌ์ฉ์์๊ฒ ์นด์นด์ค ๊ณ์  ๋ก๊ทธ์ธ์ ํตํ ์ธ์ฆ์ ์์ฒญ
- ์ฌ์ฉ์๊ฐ ์นด์นด์ค๊ณ์ ์ผ๋ก ๋ก๊ทธ์ธ
- ์นด์นด์ค ์ธ์ฆ ์๋ฒ๊ฐ ์ฌ์ฉ์์๊ฒ ๋์ ํ๋ฉด์ ์ถ๋ ฅํ์ฌ ์ธ๊ฐ๋ฅผ ์ํ ์ฌ์ฉ์ ๋์๋ฅผ ์์ฒญ
  - ๋์ํญ๋ชฉ ์ค์ ์ ๋ฐ๋ผ์ ๋ค๋ฆ
- ์ฌ์ฉ์๊ฐ ํ์ ๋์ ํญ๋ชฉ, ์ด ์ธ ์ํ๋ ๋์ ํญ๋ชฉ์ ๋์ํ ๋ค ๊ณ์ํ๊ธฐ ๋ฒํผ ๋๋ฆ
- ์นด์นด์ค ์ธ์ฆ ์๋ฒ๋ ์๋น์ค ์๋ฒ์ Redirect URI ์ธ๊ฐ ์ฝ๋๋ฅผ ์ ๋ฌ

#### 2๏ธโฃ ํ ํฐ ๋ฐ๊ธฐ
![image](https://user-images.githubusercontent.com/87464750/216277936-206bad5c-1d75-4f44-89fd-6a1f5cfb73f1.png)

- ์๋น์ค ์๋ฒ๊ฐ Redirect URI๋ก ์ ๋ฌ๋ฐ์ ์ธ๊ฐ ์ฝ๋๋ก ํ ํฐ ๋ฐ๊ธฐ๋ฅผ ์์ฒญ
- ์นด์นด์ค ์ธ์ฆ ์๋ฒ๊ฐ ํ ํฐ์ ๋ฐ๊ธํด ์๋น์ค ์๋ฒ์ ์ ๋ฌ

#### 3๏ธโฃ ์ฌ์ฉ์ ๋ก๊ทธ์ธ ์ฒ๋ฆฌ
![image](https://user-images.githubusercontent.com/87464750/216278513-69b221aa-d9bd-405b-898e-72be65abb587.png)

- ์๋น์ค ์๋ฒ๊ฐ ๋ฐ๊ธ๋ฐ์ ์์ธ์ค ํ ํฐ์ผ๋ก ์ฌ์ฉ์ ์ ๋ณด ๊ฐ์ ธ์ค๊ธฐ๋ฅผ ์์ฒญํด ์ฌ์ฉ์์ ํ์๋ฒํธ ๋ฐ ์ ๋ณด๋ฅผ ์กฐํํ์ฌ ์๋น์ค ํ์์ธ์ง ํ์ธ
- ์๋น์ค ํ์ ์ ๋ณด ํ์ธ ๊ฒฐ๊ณผ์ ๋ฐ๋ผ ์๋น์ค ๋ก๊ทธ์ธ ๋๋ ํ์ ๊ฐ์ ๊ณผ์ ์ ์งํ
- ์ด ์ธ ์๋น์ค์์ ํ์ํ ๋ก๊ทธ์ธ ์ ์ฐจ๋ฅผ ์ํํ ํ, ์นด์นด์ค ๋ก๊ทธ์ธํ ์ฌ์ฉ์์ ์๋น์ค ๋ก๊ทธ์ธ ์ฒ๋ฆฌ๋ฅผ ์๋ฃ








