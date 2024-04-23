package com.example.demae.domain.user.controller;

import com.example.demae.domain.user.entity.User;
import com.example.demae.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserControllerPage {

    @GetMapping
    public String signUpPage(){
        return "user/signUp";
    }

    @GetMapping("/login")
    public String loginFormPage(){
        return "user/login";
    }

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        User user = userDetails.getUser();

        if (user.getRole().name().equals("ADMIN") && user.getStore() != null) {
            model.addAttribute("storeId", user.getStore().getId());
            return "user/adminMain";
        }
        if (user.getRole().name().equals("ADMIN") && user.getStore() == null) {
            return "user/adminMain";
        }
        return "user/main";
    }
}
