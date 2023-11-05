package com.airconmoa.airconmoa.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@ToString(exclude = "userId")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    /*
        - created_at
        - updated_at
        - status
        는 baseEntity 생성
     */
    private String authId;
    private String authType;
    private String email;
    private String phoneNumber; // 휴대폰 번호는 안받는게 나을지도?
    private String nickname;
    private String userPhoto;
    private String uid;//파이어베이스 인증
    private String deviceToken;
    private Role role;

    /*
    @OneToMany(mappedBy = "excutions")
    private List<Excution> excutions;
    @OneToMany(mappedBy = "request_estimates")
    private List<Estimate> requestEstimates;
     */
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
}