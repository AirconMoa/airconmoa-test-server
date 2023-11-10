package com.airconmoa.airconmoa.requestEstimate.service;

import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.requestEstimate.repository.RequestEstimateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestEstimateService {
    private final RequestEstimateRepository requestEstimateRepository;

    @Transactional
    public RequestEstimate createRequestEstimate(RequestEstimate requestEstimate) {
        return requestEstimateRepository.save(requestEstimate);
    }
}
