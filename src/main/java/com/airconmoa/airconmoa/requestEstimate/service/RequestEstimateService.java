package com.airconmoa.airconmoa.requestEstimate.service;

import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.domain.User;
import com.airconmoa.airconmoa.requestEstimate.dto.PostRequestEstimateReq;
import com.airconmoa.airconmoa.requestEstimate.repository.RequestEstimateRepository;
import com.airconmoa.airconmoa.user.repository.UserRepository;
import com.airconmoa.airconmoa.util.UtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestEstimateService {

    private final RequestEstimateRepository requestEstimateRepository;
    private final UserRepository userRepository;
    private final UtilService utilService;

    @Transactional
    public String saveRequestEstimate(String userEmail, PostRequestEstimateReq postRequestEstimateReq) {
        User user = utilService.findByUserEmailWithValidation(userEmail);
        RequestEstimate requestEstimate = new RequestEstimate(user, postRequestEstimateReq);
        requestEstimateRepository.save(requestEstimate);
        return "견적 요청서 작성이 완료되었어요!";
    }

    public PostRequestEstimateReq getRequestEstimate(String companyEmail, Long requestEstimateId) {
        utilService.findByCompanyEmailWithValidation(companyEmail);
        RequestEstimate requestEstimate = utilService.findByRequestEstimateIdWithValidation(requestEstimateId);
        return new PostRequestEstimateReq(requestEstimate);
    }

}
