package com.example.demae.controller;


import com.example.demae.dto.login.SignupRequestDto;
import com.example.demae.entity.User;
import com.example.demae.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User signup(@Validated @RequestBody SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

}
