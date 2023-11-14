package com.airconmoa.airconmoa.company.service;

import com.airconmoa.airconmoa.company.dto.*;
import com.airconmoa.airconmoa.company.repository.CompanyRepository;
import com.airconmoa.airconmoa.config.jwt.JwtTokenUtil;
import com.airconmoa.airconmoa.constant.InstallInfo;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.requestEstimate.repository.RequestEstimateRepository;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponseStatus;
import com.airconmoa.airconmoa.util.UtilService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RequestEstimateRepository requestEstimateRepository;
    private final UtilService utilService;

    @Value("${jwt.secret}")
    private String secretKey;
    public CompanySignupRes signup(CompanySignupReq request) throws BaseException {
        if(isExist(request.getEmail())){
            throw new BaseException(BaseResponseStatus.ALREADY_EXIST_EMAIL);
        }
        Company company = Company.builder()
                .companyNumber(request.getCompanyNumber())
                .companyAddress(request.getAddress())
                .companyEmail(request.getEmail())
                .companyName(request.getCompanyName())
                .password(request.getPassword())
                .build();

        if(company == null) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

        companyRepository.save(company);
        companyRepository.flush();

        return CompanySignupRes.builder()
                .companyId(company.getCompanyId())
                .email(company.getCompanyEmail())
                .companyName(company.getCompanyName())
                .build();
    }

    private boolean isExist(String email) {
        Company company = companyRepository.findCompanyByCompanyEmail(email);
        if(company == null){
            return false;
        }return true;
    }

    public Company getCompanyByEmail(String email) {
        Company company = companyRepository.findCompanyByCompanyEmail(email);
        return company;
    }

    public CompanyInfoRes getCompanyInfo(String email) throws BaseException {
        Company company = getCompanyByEmail(email);
        if(company == null) {
            throw new BaseException(BaseResponseStatus.NONE_EXIST_EMAIL);
        }

        return new CompanyInfoRes(company);
    }

    public List<GetRequestEstimateRes> getRequestEstimateList(String email) {
        utilService.findByCompanyEmailWithValidation(email); // 고객이 요청할 경우 예외 발생
        List<RequestEstimate> requestEstimateList = requestEstimateRepository.findAll();
        List<GetRequestEstimateRes> getRequestEstimateResList = new ArrayList<>();

        for (RequestEstimate requestEstimate : requestEstimateList) {
            GetRequestEstimateRes getRequestEstimateRes = new GetRequestEstimateRes();

            // requestEstimateId를 추출
            Long requestEstimateId = requestEstimate.getRequestEstimateId();
            getRequestEstimateRes.setRequestEstimateId(requestEstimateId);

            // profileUrl을 추출
            String profileUrl = requestEstimate.getUser().getUserPhoto();
            if (profileUrl != null) {
                getRequestEstimateRes.setPorfileUrl(profileUrl);
            } else {
                getRequestEstimateRes.setPorfileUrl(null);
            }

            // userEmail을 추출
            String userNickname = requestEstimate.getUser().getNickname();
            getRequestEstimateRes.setUserNickname(userNickname);

            // InstallInfo를 추출
            InstallInfo installInfo = requestEstimate.getInstallInfo();
            getRequestEstimateRes.setInstallInfo(installInfo);

            // installationDate를 추출
            LocalDate installationDate = requestEstimate.getInstallationDate();
            getRequestEstimateRes.setInstallationDate(installationDate);

            getRequestEstimateResList.add(getRequestEstimateRes);
        }

        return getRequestEstimateResList;
    }

    public String login(CompanyLoginReq request) throws BaseException {
        Company company = companyRepository.findCompanyByCompanyEmail(request.getEmail());

        if(company == null) { // email에 해당하는 회사가 존재하지 않음
            throw new BaseException(BaseResponseStatus.NONE_EXIST_EMAIL);
        }

        if(!company.getPassword().equals(request.getPassword())){ // 비밀번호 잘못 입력함
            throw new BaseException(BaseResponseStatus.PASSWORD_MISSMATCH);
        }

        // 로그인 성공 => Jwt Token 발급
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        String jwtToken = JwtTokenUtil.createToken(company.getCompanyEmail(), secretKey, expireTimeMs);
        return jwtToken;
    }
}
