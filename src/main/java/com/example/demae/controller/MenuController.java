package com.example.demae.controller;

import com.example.demae.dto.menu.MenuRequestDto;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public String home(){return "menu";}
    @PostMapping("/createMenu")
    public String createMenu(@RequestBody MenuRequestDto menuRequestDto){
        String message = menuService.createMenu(menuRequestDto);
        if(!message.equals("성공")){
            throw new IllegalArgumentException("실패했습니다.");
        }
        return "showMenuPage";
    }
    @GetMapping("/menu")
    public String AllMenu(Model model){
        List<MenuResponseDto> allMenu = menuService.AllMenu();
        model.addAttribute("menuList", allMenu);
        return "showMenuPage";
    }
//    @PatchMapping
//    @DeleteMapping
}


