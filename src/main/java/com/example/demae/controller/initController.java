package com.example.demae.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class initController {
    @GetMapping("/")
    public String home(){return "store";}

}
