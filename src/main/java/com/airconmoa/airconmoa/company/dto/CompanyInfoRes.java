package com.airconmoa.airconmoa.company.dto;

import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CompanyInfoRes {
    String email;
    String nickname;
    String role;
    String companyImgUrl;
    String companyAddress;

    public CompanyInfoRes(Company company){
        this.email = company.getCompanyEmail();
        this.nickname = company.getCompanyName();
        this.role = company.getRole().name();
        this.companyImgUrl = company.getCompanyImgUrl() != null ? company.getCompanyImgUrl() : null;
        this.companyAddress = company.getCompanyAddress();
    }
}
