package com.airconmoa.airconmoa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEmailRes {
    private String email;
    private String authRandomNumber;
}

