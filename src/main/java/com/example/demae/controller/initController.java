package com.example.demae.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class initController {
    @GetMapping("/test")
    public String test(){
        return "test!!!dddddwfasdfsad";
    }
}
