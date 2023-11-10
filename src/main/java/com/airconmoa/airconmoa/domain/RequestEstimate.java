package com.airconmoa.airconmoa.domain;

import com.airconmoa.airconmoa.constant.Brand;
import com.airconmoa.airconmoa.constant.BuildingType;
import com.airconmoa.airconmoa.constant.InstallInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "request_estimate")
@Getter
@NoArgsConstructor
public class RequestEstimate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestEstimateId;

    @Enumerated(EnumType.STRING)
    private InstallInfo installInfo;

    private String installAddress;

    @Enumerated(EnumType.STRING)
    private BuildingType buildingType;

    private int amount;

    private LocalDate installationDate;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
