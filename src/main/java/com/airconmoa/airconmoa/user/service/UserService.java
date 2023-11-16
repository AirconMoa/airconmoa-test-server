package com.airconmoa.airconmoa.user.service;

import com.airconmoa.airconmoa.company.dto.CompanyInfoRes;
import com.airconmoa.airconmoa.company.repository.CompanyRepository;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.ResponseEstimate;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.responseEstimate.repository.ResponseEstimateRepository;
import com.airconmoa.airconmoa.user.dto.GetResponseEstimateRes;
import com.airconmoa.airconmoa.user.dto.GetS3Res;
import com.airconmoa.airconmoa.user.repository.UserRepository;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.util.S3Service;
import com.airconmoa.airconmoa.util.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final S3Service s3Service;

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

    @Transactional
    public String modifyUserImage(String email, MultipartFile multipartFile) throws BaseException {
        try {
            User user = utilService.findByUserEmailWithValidation(email);
            String imgUrl = user.getProfileUrl();
            if(imgUrl == null) { // 프로필이 미등록된 업체가 변경을 요청하는 경우
                GetS3Res getS3Res;
                if(multipartFile != null) {
                    getS3Res = s3Service.uploadSingleFile(multipartFile);
                    user.setUserImg(getS3Res);
                    userRepository.save(user);
                }
            }
            else { // 프로필을 이미 등록한 업체가 변경을 요청하는 경우
                // 1. 버킷에서 삭제
                deleteImage(user.getProfileUrl());
                // 2. 업체의 이미지를 null로 초기화
                user.setUserImg(null);
                if(multipartFile != null) {
                    GetS3Res getS3Res = s3Service.uploadSingleFile(multipartFile);
                    user.setUserImg(getS3Res);
                }
                userRepository.save(user);
            }
            return "유저 이미지 설정이 완료되었습니다.";
        } catch (BaseException exception) {
            throw new BaseException(exception.getStatus());
        }
    }

    @Transactional
    public void deleteImage(String fileName) {
        s3Service.deleteFile(fileName);
    }
}
