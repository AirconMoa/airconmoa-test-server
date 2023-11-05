package com.airconmoa.airconmoa.user.controller;

import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/info")
    public String userInfo(Authentication auth) {
        Optional<User> op_loginUser = userService.getUserByEmail(auth.getName());
        User loginUser = op_loginUser.get();

        return String.format("email : %s\nnickname : %s\nrole : %s",
                loginUser.getEmail(), loginUser.getNickname(), loginUser.getRole().name());
    }
}
