package com.airconmoa.airconmoa.responseEstimate.controller;

import com.airconmoa.airconmoa.requestEstimate.dto.PostRequestEstimateReq;
import com.airconmoa.airconmoa.requestEstimate.service.RequestEstimateService;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponse;
import com.airconmoa.airconmoa.responseEstimate.dto.PostResponseEstimateReq;
import com.airconmoa.airconmoa.responseEstimate.service.ResponseEstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/response_estimate")
@RequiredArgsConstructor
public class ResponseEstimateController {

    private final ResponseEstimateService responseEstimateService;

    /** 업체 측에서 견적서를 작성하기 위해 사용하는 API **/
    @PostMapping("")
    public BaseResponse<String> createRequestEstimate(Authentication auth,
                                                      @RequestBody PostResponseEstimateReq postResponseEstimateReq) {
        try {
            String companyEmail = auth.getName();
            return new BaseResponse<>(responseEstimateService.saveResponseEstimate(companyEmail, postResponseEstimateReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
