package com.example.demae.domain.user.service;

import com.example.demae.domain.user.dto.SignupRequestDto;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.entity.UserRoleEnum;
import com.example.demae.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.token}")
    private String ADMIN_TOKEN;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignupRequestDto requestDto) {

        String password = passwordEncoder.encode(requestDto.getUserPassword());

        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.getAdmin().equals("admin") && requestDto.getAdminToken().equals(ADMIN_TOKEN)){
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(requestDto,role,password);
        userRepository.save(user);
    }

    public User findUser(String userEmail) {
        return userRepository.findByUserEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("이메일이 없습니다."));
    }
}
