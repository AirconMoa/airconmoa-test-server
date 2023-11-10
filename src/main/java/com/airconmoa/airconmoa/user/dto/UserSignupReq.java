package com.airconmoa.airconmoa.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupReq {
    private String accessToken;
    private String authType;
}
