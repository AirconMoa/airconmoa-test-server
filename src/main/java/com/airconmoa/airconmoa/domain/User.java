package com.airconmoa.airconmoa.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@ToString(exclude = "userId")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    /*
        - created_at
        - updated_at
        - status
        는 baseEntity 생성
     */
    @Column(name = "auth_id")
    private String authId;
    @Column(name = "auth_type")
    private String authType;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber; // 휴대폰 번호는 안받는게 나을지도?
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "user_photo")
    private String userPhoto;

    /*
    @OneToMany(mappedBy = "excutions")
    private List<Excution> excutions;
    @OneToMany(mappedBy = "request_estimates")
    private List<Estimate> requestEstimates;
     */
    private User(String authType, String email, String nickname, String userPhoto) {
        this.authType = authType;
        this.email = email;
        this.nickname = nickname;
        this.userPhoto = userPhoto;
        authId = null;
        phoneNumber = null;
    }

    public static User createNewUser(String authType, String email, String nickname, String userPhoto) {
        return new User(authType, email, nickname, userPhoto);
    }
}