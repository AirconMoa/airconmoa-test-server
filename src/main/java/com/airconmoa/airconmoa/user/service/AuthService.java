package com.airconmoa.airconmoa.user.service;


import com.airconmoa.airconmoa.config.jwt.JwtTokenUtil;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponseStatus;
import com.airconmoa.airconmoa.user.dto.PostOauthLoginRes;
import com.airconmoa.airconmoa.user.dto.PostUidDeviceTokenReq;
import com.airconmoa.airconmoa.user.dto.SignupReq;
import com.airconmoa.airconmoa.user.dto.UserSignupReq;
import com.airconmoa.airconmoa.user.repository.UserRepository;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.util.UtilService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.Jwt;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.aspectj.weaver.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.airconmoa.airconmoa.response.BaseResponseStatus.LOGIN_INFO_CANNOT_BE_NULL;
import static com.airconmoa.airconmoa.response.BaseResponseStatus.NONE_EXIST_USER;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UtilService utilService;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;

    @Value("${jwt.secret}")
    private String secretKey;
    public User saveUser(String authType,String accessToken){
        User user = null;
        if(authType.equals("kakao")) {
            user = findKakao(accessToken);
        }
        else if(authType.equals("naver")){
            //user = findNaver(accessToken);
        }
        return userRepository.save(user);
    }

    public PostOauthLoginRes oauthLogin(String authType, String accessToken) throws BaseException {
        if (authType.isEmpty() || accessToken.isEmpty()) {
            throw new BaseException(LOGIN_INFO_CANNOT_BE_NULL);
        }
        else {
            User user = null;
            String jwtToken;
            if(authType.equals("kakao")) {
                user = findKakao(accessToken);
                log.debug("user", user.toString());
            }
            else if(authType.equals("naver")) {
                // user = findNaver(accessToken);
                // jwtToken = login(authType, accessToken);
            }
            long expireTimeMs = 6 * 1000 * 60 * 60;     // Token 유효 시간 = 6시간
            jwtToken = JwtTokenUtil.createToken(user.getEmail(), secretKey, expireTimeMs);
            return new PostOauthLoginRes(user.getUserId(), user.getEmail(), jwtToken);
        }
    }

    public String login(String authType,String accessToken){
        User find_user = null;
        if(authType.equals("kakao")) {
            find_user = findKakao(accessToken);
        }
        else if(authType.equals("naver")){
            //find_user = findNaver(accessToken);
        }
        User user = userRepository.findByEmail(find_user.getEmail()).get();
        if(user == null) {
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }
        // 로그인 성공 => Jwt Token 발급
        long expireTimeMs = 6 * 1000 * 60 * 60;     // Token 유효 시간 = 6시간
        String jwtToken = JwtTokenUtil.createToken(user.getEmail(), secretKey, expireTimeMs);
        return jwtToken;
    }

    @Transactional
    public String logout(String email) throws BaseException{
        try {
            utilService.findByUserEmailWithValidation(email);
            String accessToken = jwtTokenUtil.getJwt();
            //엑세스 토큰 남은 유효시간
            Long expiration = jwtTokenUtil.getExpiration(accessToken, secretKey);
            //Redis Cache에 저장
            redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
            return "로그아웃 되었습니다.";
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGOUT);
        }

    }

    public User findKakao(String accessToken) {
        try {
            String reqURL = "https://kapi.kakao.com/v2/user/me";
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + accessToken); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            //Gson 라이브러리로 JSON파싱
            JsonObject object = (JsonObject) JsonParser.parseString(result);
            String id = object.get("id").getAsString();
            System.out.println("id = " + id);
            String nickname = object.getAsJsonObject("properties").get("nickname").getAsString();
            System.out.println(nickname);
            String email = object.getAsJsonObject("kakao_account").get("email").getAsString();
            System.out.println(email);
            if(userRepository.findByEmailCount(email) >= 1) { // 기존 유저
                User user = userRepository.findByEmail(email).orElse(null);
                return user;
            }
            String photo = null;
            if(object.getAsJsonObject("properties").get("profile_image") != null) {
                photo = object.getAsJsonObject("properties").get("profile_image").getAsString();
                System.out.println(photo);
            }
            // 신규 유저
            User user = User.builder().authId(id).authType("kakao").nickname(nickname).email(email).profileUrl(photo).build();
            userRepository.save(user);
            userRepository.flush();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String saveUidAndToken(String userEmail, PostUidDeviceTokenReq postUidDeviceTokenReq) throws BaseException {
        User user = userRepository.findByEmail(userEmail).orElse(null);
        if(user == null) {
            throw new BaseException(NONE_EXIST_USER);
        }

        user.updateUid(postUidDeviceTokenReq.getUid());
        user.updateDeviceToken(postUidDeviceTokenReq.getDeviceToken());
        userRepository.save(user);

        return "UID와 디바이스 토큰이 저장되었습니다.";
    }

    public String getKakaoAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=734adc2b65a1f754d94ec9ff14996ac2");
            sb.append("&redirect_uri=http://localhost:9000/oauth/kakao");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public Boolean isTokenExpired(String accessToken) {
        String token = accessToken.split(" ")[1];
        return JwtTokenUtil.isExpired(token, secretKey);
    }
}
