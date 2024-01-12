package com.example.demae.config;

import com.example.demae.dto.login.SignupRequestDto;
import com.example.demae.entity.User;
import com.example.demae.enums.UserRoleEnum;
import com.example.demae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserConfig {

    @Value("${jwt.token}")
    private String ADMIN_TOKEN;

    private final UserRepository userRepository;

    public UserRoleEnum checkRole(SignupRequestDto requestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin() && requestDto.getAdminToken().equals(ADMIN_TOKEN)){
            role = UserRoleEnum.ADMIN;
        }
        return role;
    }

    public void checkEmail(String email) {
        Optional<User> isEmail = userRepository.findByEmail(email);
        if(isEmail.isPresent()){
            throw new IllegalArgumentException("해당 이메일은 이미 존재합니다");
        }
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException(
                        "not found " + email
                )
        );
    }
}
