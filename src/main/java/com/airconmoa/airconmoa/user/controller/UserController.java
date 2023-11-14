package com.airconmoa.airconmoa.user.controller;

import com.airconmoa.airconmoa.company.dto.GetRequestEstimateRes;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponse;
import com.airconmoa.airconmoa.user.dto.GetResponseEstimateRes;
import com.airconmoa.airconmoa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    /** 나에게 도착한 견적서 조회 **/
    @GetMapping("/response-estimate")
    public BaseResponse<List<GetResponseEstimateRes>> getResponseEstimateList(Authentication auth) {
        try {
            String email = auth.getName();
            return new BaseResponse<>(userService.getResponseEstimateResList(email));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/info")
    public String userInfo(Authentication auth) {
        Optional<User> op_loginUser = userService.getUserByEmail(auth.getName());
        User loginUser = op_loginUser.get();

        return String.format("email : %s\nnickname : %s\nrole : %s",
                loginUser.getEmail(), loginUser.getNickname(), loginUser.getRole().name());
    }
}
