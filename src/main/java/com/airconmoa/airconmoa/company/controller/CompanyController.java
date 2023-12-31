package com.airconmoa.airconmoa.company.controller;

import com.airconmoa.airconmoa.company.dto.*;
import com.airconmoa.airconmoa.company.service.CompanyService;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping("/signup")
    public BaseResponse<CompanySignupRes> signUp(@RequestBody CompanySignupReq request) {
        try {
            return new BaseResponse<>(companyService.signup(request));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody CompanyLoginReq request) {
        try {
            return new BaseResponse<>(companyService.login(request));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 로그아웃
    @PostMapping("/log-out") // Redis가 켜져있어야 동작한다.
    public BaseResponse<String> logoutCompany(Authentication auth) {
        try {
            String email = auth.getName();
            return new BaseResponse<>(companyService.logout(email));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 업체 마이페이지 정보 출력
    @GetMapping("/company-info")
    public BaseResponse<CompanyInfoRes> getCompanyInfo(Authentication auth) {
        try {
            String email = auth.getName();
            return new BaseResponse<>(companyService.getCompanyInfo(email));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /** 시스템 에어컨 업체에서 견적 요청서를 조회하기 위한 API **/
    @GetMapping("/request-estimate")
    public BaseResponse<List<GetRequestEstimateRes>> getRequestEstimateList(Authentication auth) {
        try {
            String email = auth.getName();
            return new BaseResponse<>(companyService.getRequestEstimateList(email));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /** 시스템 에어컨 업체의 이미지를 설정하는 API **/
    @PatchMapping("/image")
    public BaseResponse<String> modifyCompanyImage(Authentication auth,
                                                   @RequestPart(value = "image", required = false) MultipartFile multipartFile) {
        try {
            String email = auth.getName();
            return new BaseResponse<>(companyService.modifyCompanyImage(email, multipartFile));
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
