package com.airconmoa.airconmoa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetResponseEstimateRes {
    Long responseEstimateId;
    String companyName;
    String companyAddress;
    Integer price;
}
