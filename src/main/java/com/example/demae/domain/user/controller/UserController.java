package com.example.demae.domain.user.controller;

import com.example.demae.domain.user.dto.SignupRequestDto;
import com.example.demae.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequestDto signupRequestDto){
        userService.signUp(signupRequestDto);
        return ResponseEntity.ok("성공");
    }
}