package com.example.demae.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class initController {
    @GetMapping("/main")
    public String main(){return "main";}

    @GetMapping("/store")
    public String store(){return "store";}


}
