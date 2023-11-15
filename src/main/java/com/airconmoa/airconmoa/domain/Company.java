package com.airconmoa.airconmoa.domain;

import com.airconmoa.airconmoa.user.dto.GetS3Res;
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

    @Column(nullable = true)
    private String companyImgUrl;

    @Column(nullable = true)
    private String companyImgFileName;

    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private ResponseEstimate responseEstimate;

    //private List<CompnayPhoto> compnayPhotos = new ArrayList<>();
    //private List<Region> activeAreas = new ArrayList<>();
    //private List<Execution> executions = new ArrayList<>();
    //private List<Estimate> estimates = new ArrayList<>();
    @Builder
    private Company(String companyName, String companyNumber, String companyEmail,
                    String password, String companyAddress, String companyImgUrl, String companyImgFileName) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.companyEmail = companyEmail;
        this.password = password;
        this.companyAddress = companyAddress;
        this.companyImgUrl = companyImgUrl;
        this.companyImgFileName = companyImgFileName;
        this.role = Role.COMPANY;
    }

    public void setCompanyImg(GetS3Res getS3Res) {
        this.companyImgUrl = getS3Res.getImgUrl();
        this.companyImgFileName = getS3Res.getFileName();
    }
}