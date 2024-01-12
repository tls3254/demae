package com.example.demae.service;


import com.example.demae.dto.login.SignupRequestDto;
import com.example.demae.entity.User;
import com.example.demae.enums.UserRoleEnum;
import com.example.demae.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${jwt.token}")
    private String ADMIN_TOKEN;


    public User signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다");
        }


        UserRoleEnum role = checkRole(requestDto);

        User user = new User(requestDto,role,password);
        return userRepository.save(user);
    }


    private UserRoleEnum checkRole(SignupRequestDto requestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin() && requestDto.getAdminToken().equals(ADMIN_TOKEN)){
            role = UserRoleEnum.ADMIN;
        }
        return role;
    }
}
