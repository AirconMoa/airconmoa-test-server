package com.airconmoa.airconmoa.util;

import com.airconmoa.airconmoa.company.repository.CompanyRepository;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponseStatus;
import com.airconmoa.airconmoa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public User findByUserEmailWithValidation(String email) throws BaseException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NONE_EXIST_USER));
    }

    public Company findByCompanyEmailWithValidation(String email) throws BaseException {
        return companyRepository.findByCompanyEmail(email)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NONE_EXIST_COMPANY));
    }
}
