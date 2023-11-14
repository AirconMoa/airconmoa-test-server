package com.airconmoa.airconmoa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostUidDeviceTokenReq {
    String uid;
    String deviceToken;
}
