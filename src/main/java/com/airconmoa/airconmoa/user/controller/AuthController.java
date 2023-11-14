package com.airconmoa.airconmoa.user.controller;

import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponse;
import com.airconmoa.airconmoa.user.dto.PostOauthLoginRes;
import com.airconmoa.airconmoa.user.dto.PostUidDeviceTokenReq;
import com.airconmoa.airconmoa.user.dto.UserSignupReq;
import com.airconmoa.airconmoa.user.dto.UserSignupRes;
import com.airconmoa.airconmoa.user.service.AuthService;
import com.airconmoa.airconmoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

//    @PostMapping("/signup")
//    public ResponseEntity<UserSignupRes> signUp(@RequestBody UserSignupReq request) {
//        //프런트에게 받을 accessToken을 테스트용으로 발급한 코드
////        String token = authService.getKakaoAccessToken
////                ("CbM0_fMMugxT6r5EJvtRyPbQx1P3NEjTKa9riaSxD2o5D1YnQJq4-DV9UVQKKiVOAAABi3XSdRKi-KZYUq23DA");
//        User user = authService.saveUser(request.getAuthType(), request.getAccessToken());
//        return ResponseEntity.ok(new UserSignupRes(user.getNickname(), user.getUserId()));
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody UserSignupReq request) {
//        //프런트에게 받을 accessToken을 테스트용으로 발급한 코드
////        String token = authService.getKakaoAccessToken
////                ("E0217FS-3faKpiheVTRELn9ERbHFx3X-ogxvVJfYbFGZLQE9nmHN6mrAdc4KKiUQAAABi3XTKcqm1x-HnlkNwQ");
//        return ResponseEntity.ok(authService.login(request.getAuthType(), request.getAccessToken()));
//    }

    /** 카카오 소셜로그인 **/
    @PostMapping("/signup")
    public BaseResponse<PostOauthLoginRes> oauthLogin(@RequestBody UserSignupReq request) {
        try {
            return new BaseResponse<>(authService.oauthLogin(request.getAuthType(), request.getAccessToken()));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /** UID와 디바이스 토큰 저장 **/
    @PostMapping("/oauth/device-token")
    public BaseResponse<String> saveUidAndToken(Authentication auth, @RequestBody PostUidDeviceTokenReq postUidDeviceTokenReq) {
        try {
            String userEmail = auth.getName();
            return new BaseResponse<>(authService.saveUidAndToken(userEmail, postUidDeviceTokenReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
