package com.airconmoa.airconmoa.company.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanySignupReq {
    String email;
    String password;
    String companyName;
    String companyNumber;
    String address;
    //활동 지역
}
