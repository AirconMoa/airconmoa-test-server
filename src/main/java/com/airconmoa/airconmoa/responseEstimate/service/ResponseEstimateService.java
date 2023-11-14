package com.airconmoa.airconmoa.responseEstimate.service;

import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.domain.ResponseEstimate;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.requestEstimate.dto.PostRequestEstimateReq;
import com.airconmoa.airconmoa.requestEstimate.repository.RequestEstimateRepository;
import com.airconmoa.airconmoa.responseEstimate.dto.PostResponseEstimateReq;
import com.airconmoa.airconmoa.responseEstimate.repository.ResponseEstimateRepository;
import com.airconmoa.airconmoa.user.repository.UserRepository;
import com.airconmoa.airconmoa.util.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseEstimateService {
    private final ResponseEstimateRepository responseEstimateRepository;
    private final UtilService utilService;

    @Transactional
    public String saveResponseEstimate(String companyEmail, PostResponseEstimateReq postResponseEstimateReq) {
        Company company = utilService.findByCompanyEmailWithValidation(companyEmail);
        Long requestEstimateId = postResponseEstimateReq.getRequestEstimateId();
        RequestEstimate requestEstimate = utilService.findByRequestEstimateIdWithValidation(requestEstimateId);
        ResponseEstimate responseEstimate = new ResponseEstimate(company, requestEstimate, postResponseEstimateReq);
        responseEstimateRepository.save(responseEstimate);

        return "견적 작성이 완료되었어요!";
    }
}
