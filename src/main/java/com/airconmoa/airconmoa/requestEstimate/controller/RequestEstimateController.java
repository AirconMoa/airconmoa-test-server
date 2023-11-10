package com.airconmoa.airconmoa.requestEstimate.controller;

import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.requestEstimate.service.RequestEstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/request_estimate")
@RequiredArgsConstructor
public class RequestEstimateController {
    private final RequestEstimateService requestEstimateService;

    @PostMapping
    public ResponseEntity<RequestEstimate> createRequestEstimate(@RequestBody RequestEstimate requestEstimate) {
        RequestEstimate createdRequestEstimate = requestEstimateService.createRequestEstimate(requestEstimate);
        return ResponseEntity.created(URI.create("/request_estimate/" + createdRequestEstimate.getRequestEstimateId())).body(createdRequestEstimate);
    }
}