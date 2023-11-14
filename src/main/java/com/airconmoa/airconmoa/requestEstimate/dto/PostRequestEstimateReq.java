package com.airconmoa.airconmoa.requestEstimate.dto;

import com.airconmoa.airconmoa.constant.Brand;
import com.airconmoa.airconmoa.constant.BuildingType;
import com.airconmoa.airconmoa.constant.InstallInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostRequestEstimateReq {
    InstallInfo installInfo;
    String installAddress;
    BuildingType buildingType;
    Integer amount;
    LocalDate installationDate;
    Brand brand;
}
