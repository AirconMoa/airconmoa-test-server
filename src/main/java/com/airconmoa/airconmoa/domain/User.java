package com.airconmoa.airconmoa.domain;

import com.airconmoa.airconmoa.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@ToString(exclude = "userId")
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String authId;

    @Column(nullable = false)
    private String authType;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String userPhoto;

    @Column(nullable = true)
    private String uid;

    @Column(nullable = true)
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /*
    @OneToMany(mappedBy = "excutions")
    private List<Excution> excutions;
    @OneToMany(mappedBy = "request_estimates")
    private List<Estimate> requestEstimates;
     */

    @OneToMany(mappedBy = "user")
    private List<RequestEstimate> requestEstimates = new ArrayList<>();
  
    @Builder
    private User(String authId, String authType, String email, String nickname, String userPhoto) {
        this.authType = authType;
        this.email = email;
        this.nickname = nickname;
        this.userPhoto = userPhoto;
        this.authId = authId;
        this.role = Role.USER;
        phoneNumber = null;
    }

    public void updateUid(String uid) { this.uid = uid; }
    public void updateDeviceToken(String deviceToken) { this.deviceToken = deviceToken; }
}