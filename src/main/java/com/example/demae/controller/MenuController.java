package com.example.demae.controller;

import com.example.demae.dto.menu.MenuRequestDto;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.jwt.JwtUtil;
import com.example.demae.security.UserDetailsImpl;
import com.example.demae.service.MenuService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final JwtUtil jwtUtil;

    @GetMapping
    public String home(){return "menu";}

    @PostMapping("/createMenu")
    public String createMenu(
                             @RequestBody MenuRequestDto menuRequestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        String email = userDetails.getUser().getEmail();
        String message = menuService.createMenu(menuRequestDto,email);
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
    @PatchMapping("/menu/{storeId}/{menuId}")
    public String patchMemu(@PathVariable Long storeId,
                            @PathVariable Long menuId,
                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestBody MenuRequestDto menuRequestDto){
        String email = userDetails.getUser().getEmail();
        menuService.patchMenu(storeId,menuId,menuRequestDto,email);

        return "showMenuPage";
    }
    @DeleteMapping("/menu/{storeId}/{menuId}")
    public String deleteMenu(@PathVariable Long storeId,
                             @PathVariable Long menuId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        String email = userDetails.getUser().getEmail();
        menuService.deleteMenu(storeId,menuId,email);
        return "showMenuPage";
    }
}
