package com.airconmoa.airconmoa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "company")
@Getter
@ToString(exclude = "companyId")
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    /*
        - created_at
        - updated_at
        - status
        는 baseEntity 생성
     */
    private String companyName;
    private String companyNumber;
    private String companyEmail;
    private String password;
    private String companyAddress;
    private Role role;
    //private List<CompnayPhoto> compnayPhotos = new ArrayList<>();
    //private List<Region> activeAreas = new ArrayList<>();
    //private List<Execution> executions = new ArrayList<>();
    //private List<Estimate> estimates = new ArrayList<>();
    @Builder
    private Company(String companyName, String companyNumber, String companyEmail, String password, String companyAddress){
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyEmail = companyEmail;
        this.password = password;
        this.companyAddress = companyAddress;
        this.role = Role.COMPANY;
    }
}