package com.airconmoa.airconmoa.user.controller;

import com.airconmoa.airconmoa.config.jwt.JwtTokenFilter;
import com.airconmoa.airconmoa.config.jwt.JwtTokenUtil;
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

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

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

    // 로그아웃
    @PostMapping("/log-out") // Redis가 켜져있어야 동작한다.
    public BaseResponse<String> logoutUser(Authentication auth) {
        try {
            String email = auth.getName();
            return new BaseResponse<>(authService.logout(email));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 액세스 토큰의 만료여부 확인
    @GetMapping("check-token")
    public BaseResponse<Boolean> isTokenExpired(Authentication auth) {
        String accessToken = jwtTokenUtil.getJwt();
        return new BaseResponse<>(authService.isTokenExpired(accessToken));
    }

}
