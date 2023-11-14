package com.airconmoa.airconmoa.responseEstimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostResponseEstimateReq {
    Long requestEstimateId; // 견적 요청서의 ID
    Integer price; // 시공 가격
    String contents; // 수정 및 변동 사항
    String additionalInfo; // 추가 전달 사항
}
