package com.airconmoa.airconmoa.company.controller;

import com.airconmoa.airconmoa.company.dto.CompanyLoginReq;
import com.airconmoa.airconmoa.company.dto.CompanySignupReq;
import com.airconmoa.airconmoa.company.dto.CompanySignupRes;
import com.airconmoa.airconmoa.company.service.CompanyService;
import com.airconmoa.airconmoa.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/signup")
    public ResponseEntity<CompanySignupRes> signUp(@RequestBody CompanySignupReq request) {
        Company company = companyService.signup(request);
        if(company == null) return null; //예회처리 할 예정
        CompanySignupRes res = CompanySignupRes.builder()
                .companyId(company.getCompanyId())
                .email(company.getCompanyEmail())
                .companyName(company.getCompanyName())
                .build();
        return ResponseEntity.ok(res);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CompanyLoginReq request) {
        return ResponseEntity.ok(companyService.login(request));

    }
    //업체 정보 출력
    @GetMapping("/info")
    public String companyInfo(Authentication auth) {
        Company company = companyService.getCompanyByEmail(auth.getName());

        return String.format("email : %s\nnickname : %s\nrole : %s",
                company.getCompanyEmail(), company.getCompanyName(), company.getRole().name());
    }
}
