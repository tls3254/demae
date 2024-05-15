package com.example.demae.domain.user.entity;

import com.example.demae.domain.user.dto.SignupRequestDto;
import com.example.demae.entity.Store;
import com.example.demae.global.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userPhone;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userAddress;

    @Column(nullable = false)
    private Long userPoint;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRole;

    private String refreshToken;

    @OneToOne
    @JoinColumn(name ="store_id")
    @JsonIgnore
    private Store store;

    public User(SignupRequestDto requestDto, UserRoleEnum role, String password) {
        this.userEmail = requestDto.getUserEmail();
        this.userName = requestDto.getUserName();
        this.userAddress = requestDto.getUserAddress();
        this.userPhone = requestDto.getUserPhone();
        this.userPassword = password;
        this.userPoint = 1000000L;
        this.userRole = role;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
