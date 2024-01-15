package com.example.demae.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class initController {
    @GetMapping("/main")
    public String main(){return "main";}

    @GetMapping("/store")
    public String store(){return "store";}

}
