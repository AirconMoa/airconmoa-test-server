package com.airconmoa.airconmoa.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@NoArgsConstructor
public class SignupReq {
    private String accessToken;
    private String authType;
}
