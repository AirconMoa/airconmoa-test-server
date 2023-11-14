package com.airconmoa.airconmoa.domain;

import com.airconmoa.airconmoa.constant.Brand;
import com.airconmoa.airconmoa.constant.BuildingType;
import com.airconmoa.airconmoa.constant.InstallInfo;
import com.airconmoa.airconmoa.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
