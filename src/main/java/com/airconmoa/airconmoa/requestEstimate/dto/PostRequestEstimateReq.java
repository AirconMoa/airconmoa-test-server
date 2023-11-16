package com.airconmoa.airconmoa.requestEstimate.dto;

import com.airconmoa.airconmoa.constant.Brand;
import com.airconmoa.airconmoa.constant.BuildingType;
import com.airconmoa.airconmoa.constant.InstallInfo;
import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.RequestEstimate;
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

    public PostRequestEstimateReq(RequestEstimate requestEstimate){
        this.installInfo = requestEstimate.getInstallInfo();
        this.installAddress = requestEstimate.getInstallAddress();
        this.buildingType = requestEstimate.getBuildingType();
        this.amount = requestEstimate.getAmount();
        this.installationDate = requestEstimate.getInstallationDate();
        this.brand = requestEstimate.getBrand();
    }
}
