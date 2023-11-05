package com.airconmoa.airconmoa.company.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyLoginReq {
    String email;
    String password;
}
