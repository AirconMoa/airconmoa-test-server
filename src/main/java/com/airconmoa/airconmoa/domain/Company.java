package com.airconmoa.airconmoa.domain;

import com.airconmoa.airconmoa.util.BaseTimeEntity;
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
public class Company extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    /*
        - created_at
        - updated_at
        - status
        는 baseEntity 생성
     */
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyNumber;

    @Column(nullable = false)
    private String companyEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String companyAddress;

    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResponseEstimate responseEstimate;

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