package com.airconmoa.airconmoa.company.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanySignupRes {
    long companyId;
    String companyName;
    String email;
    @Builder
    public CompanySignupRes(long companyId,String companyName, String email){
        this.companyId = companyId;
        this.companyName = companyName;
        this.email = email;
    }
}
