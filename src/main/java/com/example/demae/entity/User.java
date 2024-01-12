package com.example.demae.entity;

import com.example.demae.dto.login.SignupRequestDto;
import com.example.demae.enums.Timestamped;
import com.example.demae.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long point;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    public User(SignupRequestDto requestDto, UserRoleEnum role, String password) {
        this.email = requestDto.getEmail();
        this.name = requestDto.getName();
        this.address = requestDto.getAddress();
        this.point = requestDto.getPoint();
        this.role = role;
        this.phone = requestDto.getPhone();
        this.password = password;
    }
}
