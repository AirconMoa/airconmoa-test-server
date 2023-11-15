package com.airconmoa.airconmoa.user.service;

import com.airconmoa.airconmoa.company.dto.CompanyInfoRes;
import com.airconmoa.airconmoa.company.dto.GetRequestEstimateRes;
import com.airconmoa.airconmoa.company.repository.CompanyRepository;
import com.airconmoa.airconmoa.constant.InstallInfo;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.domain.ResponseEstimate;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponseStatus;
import com.airconmoa.airconmoa.responseEstimate.repository.ResponseEstimateRepository;
import com.airconmoa.airconmoa.user.dto.GetResponseEstimateRes;
import com.airconmoa.airconmoa.user.repository.UserRepository;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.util.UtilService;
import com.amazonaws.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final ResponseEstimateRepository responseEstimateRepository;
    private final UtilService utilService;

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<GetResponseEstimateRes> getResponseEstimateResList(String email) {
        utilService.findByUserEmailWithValidation(email); // 업체가 요청할 경우 예외 발생
        List<ResponseEstimate> responseEstimateList = responseEstimateRepository.findMyResponseEstimate(email);
        List<GetResponseEstimateRes> getResponseEstimateResList = new ArrayList<>();

        for (ResponseEstimate responseEstimate : responseEstimateList) {
            GetResponseEstimateRes getResponseEstimateRes = new GetResponseEstimateRes();

            // responseEstimateId를 추출
            Long responseEstimateId = responseEstimate.getResponseEstimateId();
            getResponseEstimateRes.setResponseEstimateId(responseEstimateId);

            // companyName을 추출
            String companyName = responseEstimate.getCompany().getCompanyName();
            getResponseEstimateRes.setCompanyName(companyName);

            // companyAddress를 추출
            String companyAddress = responseEstimate.getCompany().getCompanyAddress();
            getResponseEstimateRes.setCompanyAddress(companyAddress);

            // price를 추출
            Integer price = responseEstimate.getPrice();
            getResponseEstimateRes.setPrice(price);

            getResponseEstimateResList.add(getResponseEstimateRes);
        }

        return getResponseEstimateResList;
    }

    public List<CompanyInfoRes> getCompanyInfo() throws BaseException {
        List<Company> companyList = companyRepository.findAll();
        List<CompanyInfoRes> companyInfoResList = new ArrayList<>();

        for (Company company : companyList) {
            CompanyInfoRes companyInfoRes = new CompanyInfoRes(company);
            companyInfoResList.add(companyInfoRes);
        }

        return companyInfoResList;
    }
}
