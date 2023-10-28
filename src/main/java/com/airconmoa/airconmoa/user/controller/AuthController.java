package com.airconmoa.airconmoa.user.controller;

import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.user.dto.SignupReq;
import com.airconmoa.airconmoa.user.dto.SignupRes;
import com.airconmoa.airconmoa.user.service.AuthService;
import com.airconmoa.airconmoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupRes> signUp(@RequestBody SignupReq request) {
        //프런트에게 받을 accessToken을 테스트용으로 발급한 코드
//        String token = authService.getKakaoAccessToken
//                ("CbM0_fMMugxT6r5EJvtRyPbQx1P3NEjTKa9riaSxD2o5D1YnQJq4-DV9UVQKKiVOAAABi3XSdRKi-KZYUq23DA");
        User user = authService.saveUser(request.getAuthType(), request.getAccessToken());
        return ResponseEntity.ok(new SignupRes(user.getNickname(), user.getUserId()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody SignupReq request) {
        //프런트에게 받을 accessToken을 테스트용으로 발급한 코드
//        String token = authService.getKakaoAccessToken
//                ("E0217FS-3faKpiheVTRELn9ERbHFx3X-ogxvVJfYbFGZLQE9nmHN6mrAdc4KKiUQAAABi3XTKcqm1x-HnlkNwQ");
        return ResponseEntity.ok(authService.login(request.getAuthType(), request.getAccessToken()));
    }

}
