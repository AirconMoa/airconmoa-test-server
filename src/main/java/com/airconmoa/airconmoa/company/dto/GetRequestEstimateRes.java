package com.airconmoa.airconmoa.company.dto;

import com.airconmoa.airconmoa.constant.InstallInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetRequestEstimateRes {
    Long requestEstimateId;
    String porfileUrl;
    String userNickname;
    InstallInfo installInfo;
    LocalDate installationDate;
}
