package com.example.demae.domain.user.controller;

import com.example.demae.domain.user.dto.SignupRequestDto;
import com.example.demae.domain.user.entity.User;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> signUpSuccess(@ModelAttribute SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return ResponseEntity.ok("성공");
    }
}