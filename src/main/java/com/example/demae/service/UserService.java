package com.example.demae.service;


import com.example.demae.config.UserConfig;
import com.example.demae.dto.login.SignupRequestDto;
import com.example.demae.entity.User;
import com.example.demae.enums.UserRoleEnum;
import com.example.demae.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConfig userConfig;

    public User signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        userConfig.checkEmail(email);

        UserRoleEnum role = userConfig.checkRole(requestDto);

        User user = new User(requestDto,role,password);
        return userRepository.save(user);
    }

}
