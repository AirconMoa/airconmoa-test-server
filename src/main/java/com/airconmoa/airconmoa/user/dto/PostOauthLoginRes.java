package com.airconmoa.airconmoa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostOauthLoginRes {
    Long userId;
    String email;
    String accessToken;
}
