package com.airconmoa.airconmoa.domain;

import com.airconmoa.airconmoa.constant.Brand;
import com.airconmoa.airconmoa.constant.BuildingType;
import com.airconmoa.airconmoa.constant.InstallInfo;
import com.airconmoa.airconmoa.requestEstimate.dto.PostRequestEstimateReq;
import com.airconmoa.airconmoa.responseEstimate.dto.PostResponseEstimateReq;
import com.airconmoa.airconmoa.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "response_estimate")
@Getter
@NoArgsConstructor
public class ResponseEstimate extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseEstimateId;

    @Column(nullable = false)
    private Integer price; // 시공 가격

    @Column(nullable = false)
    private String contents; // 수정 및 변동 사항

    @Column(nullable = true)
    private String additionalInfo; // 추가 전달 사항

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_estimate_id")
    private RequestEstimate requestEstimate;

    public ResponseEstimate(Company company, RequestEstimate requestEstimate, PostResponseEstimateReq postResponseEstimateReq) {
        this.price = postResponseEstimateReq.getPrice();
        this.contents = postResponseEstimateReq.getContents();
        this.additionalInfo = postResponseEstimateReq.getAdditionalInfo();
        this.company = company;
        this.requestEstimate = requestEstimate;
    }
}
