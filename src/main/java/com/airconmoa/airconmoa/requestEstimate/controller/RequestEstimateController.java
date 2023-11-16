package com.airconmoa.airconmoa.requestEstimate.controller;

import com.airconmoa.airconmoa.requestEstimate.dto.PostRequestEstimateReq;
import com.airconmoa.airconmoa.requestEstimate.service.RequestEstimateService;
import com.airconmoa.airconmoa.response.BaseException;
import com.airconmoa.airconmoa.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request-estimate")
@RequiredArgsConstructor
public class RequestEstimateController {
    private final RequestEstimateService requestEstimateService;

    @PostMapping("")
    public BaseResponse<String> createRequestEstimate(Authentication auth,
                                                      @RequestBody PostRequestEstimateReq postRequestEstimateReq) {
        try {
            String userEmail = auth.getName();
            return new BaseResponse<>(requestEstimateService.saveRequestEstimate(userEmail, postRequestEstimateReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 견적 요청서 세부 조회 API
    @GetMapping("")
    public BaseResponse<PostRequestEstimateReq> getRequestEstimate(Authentication auth, @RequestParam Long requestEstimateId) {
        try {
            String companyEmail = auth.getName();
            return new BaseResponse<>(requestEstimateService.getRequestEstimate(companyEmail, requestEstimateId));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}