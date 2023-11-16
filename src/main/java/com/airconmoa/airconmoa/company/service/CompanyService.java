package com.airconmoa.airconmoa.company.service;

import com.airconmoa.airconmoa.company.dto.*;
import com.airconmoa.airconmoa.company.repository.CompanyRepository;
import com.airconmoa.airconmoa.config.jwt.JwtTokenUtil;
import com.airconmoa.airconmoa.constant.InstallInfo;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.requestEstimate.repository.RequestEstimateRepository;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponseStatus;
import com.airconmoa.airconmoa.user.dto.GetS3Res;
import com.airconmoa.airconmoa.util.AES128;
import com.airconmoa.airconmoa.util.S3Service;
import com.airconmoa.airconmoa.util.Secret;
import com.airconmoa.airconmoa.util.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RequestEstimateRepository requestEstimateRepository;
    private final UtilService utilService;
    private final S3Service s3Service;
    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;

    @Value("${jwt.secret}")
    private String secretKey;
    public CompanySignupRes signup(CompanySignupReq request) throws BaseException {
        if(isExist(request.getEmail())){
            throw new BaseException(BaseResponseStatus.ALREADY_EXIST_EMAIL);
        }

        String pwd = "";
        try{
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(request.getPassword()); // 암호화코드
        }
        catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(BaseResponseStatus.PASSWORD_ENCRYPTION_ERROR);
        }

        Company company = Company.builder()
                .companyNumber(request.getCompanyNumber())
                .companyAddress(request.getAddress())
                .companyEmail(request.getEmail())
                .companyName(request.getCompanyName())
                .password(pwd)
                .companyImgUrl(null)
                .companyImgFileName(null)
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
        }

        return true;
    }

    public Company getCompanyByEmail(String email) {
        Company company = companyRepository.findCompanyByCompanyEmail(email);
        return company;
    }

    @Transactional
    public String logout(String email) throws BaseException{
        try {
            utilService.findByCompanyEmailWithValidation(email);
            String accessToken = jwtTokenUtil.getJwt();
            //엑세스 토큰 남은 유효시간
            Long expiration = jwtTokenUtil.getExpiration(accessToken, secretKey);
            //Redis Cache에 저장
            redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
            return "로그아웃 되었습니다.";
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_LOGOUT);
        }
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

        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(request.getPassword());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BaseException(BaseResponseStatus.PASSWORD_DECRYPTION_ERROR);
        }

        if (!company.getPassword().equals(password)) { // 비밀번호 잘못 입력함
            throw new BaseException(BaseResponseStatus.PASSWORD_MISSMATCH);
        }

        // 로그인 성공 => Jwt Token 발급
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        String jwtToken = JwtTokenUtil.createToken(company.getCompanyEmail(), secretKey, expireTimeMs);
        return jwtToken;
    }

    @Transactional
    public String modifyCompanyImage(String email, MultipartFile multipartFile) throws BaseException {
        try {
            Company company = utilService.findByCompanyEmailWithValidation(email);
            String imgUrl = company.getCompanyImgUrl();
            if(imgUrl == null) { // 프로필이 미등록된 업체가 변경을 요청하는 경우
                GetS3Res getS3Res;
                if(multipartFile != null) {
                    getS3Res = s3Service.uploadSingleFile(multipartFile);
                    company.setCompanyImg(getS3Res);
                    companyRepository.save(company);
                }
            }
            else { // 프로필을 이미 등록한 업체가 변경을 요청하는 경우
                // 1. 버킷에서 삭제
                deleteImage(company.getCompanyImgFileName());
                // 2. 업체의 이미지를 null로 초기화
                company.setCompanyImg(null);
                if(multipartFile != null) {
                    GetS3Res getS3Res = s3Service.uploadSingleFile(multipartFile);
                    company.setCompanyImg(getS3Res);
                }
                companyRepository.save(company);
            }
            return "업체 이미지 설정이 완료되었습니다.";
        } catch (BaseException exception) {
            throw new BaseException(exception.getStatus());
        }
    }

    @Transactional
    public void deleteImage(String fileName) {
        s3Service.deleteFile(fileName);
    }
}
