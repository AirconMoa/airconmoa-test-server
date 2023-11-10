package com.airconmoa.airconmoa.company.service;

import com.airconmoa.airconmoa.company.dto.CompanyLoginReq;
import com.airconmoa.airconmoa.company.dto.CompanySignupReq;
import com.airconmoa.airconmoa.company.repository.CompanyRepository;
import com.airconmoa.airconmoa.config.jwt.JwtTokenUtil;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    @Value("${jwt.secret}")
    private String secretKey;
    public Company signup(CompanySignupReq request){
        if(isExist(request.getEmail())){
            return null; // 예외처리 추가 예정
        }
        Company company = Company.builder()
                .companyNumber(request.getCompanyNumber())
                .companyAddress(request.getAddress())
                .companyEmail(request.getEmail())
                .companyName(request.getCompanyName())
                .password(request.getPassword())
                .build();
        return companyRepository.save(company);
    }

    private boolean isExist(String email) {
        Company company = companyRepository.findCompanyByCompanyEmail(email);
        if(company == null){
            return false;
        }return true;
    }

    public Company getCompanyByEmail(String email){
        Company company = companyRepository.findCompanyByCompanyEmail(email);
        return company;
    }
    public String login(CompanyLoginReq request){
        Company company = companyRepository.findCompanyByCompanyEmail(request.getEmail());
        if(company == null) {
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }
        if(!company.getPassword().equals(request.getPassword())){
            return "로그인 아이디 또는 비밀번호가 틀렸습니다.";
        }
        // 로그인 성공 => Jwt Token 발급
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        String jwtToken = JwtTokenUtil.createToken(company.getCompanyEmail(), secretKey, expireTimeMs);
        return jwtToken;
    }
}
