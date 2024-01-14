package com.example.demae.controller;

import com.example.demae.dto.menu.MenuRequestDto;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demae.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class MenuController {
	private final MenuService menuService;
  
    @GetMapping("/{storeId}/menu")
    public String home(Model model,@PathVariable Long storeId){
        model.addAttribute("storeId", storeId);
        return "menu";
    }
    @PostMapping("/{storeId}/createMenu")
    public String createMenu(@RequestBody MenuRequestDto menuRequestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails,
                             @PathVariable Long storeId){
        String email = userDetails.getUser().getEmail();
        String message = menuService.createMenu(storeId,menuRequestDto,email);
        if(!message.equals("성공")){
            throw new IllegalArgumentException("실패했습니다.");
        }
        return "showMenuPage";
    }
    @GetMapping("/{storeId}/allMenu")
    public String AllMenu(Model model,@PathVariable Long storeId){
        List<MenuResponseDto> allMenu = menuService.AllMenu(storeId);
        model.addAttribute("menuList", allMenu);
        return "showMenuPage";
    }
    @GetMapping("/{storeId}/selectMenu/{menuId}")
    public String selectMenu(Model model,@PathVariable Long storeId,@PathVariable Long menuId){
        MenuResponseDto selectMenu = menuService.selectMenu(storeId,menuId);
        model.addAttribute("menuOne", selectMenu);
        model.addAttribute("storeId", storeId); // 여기에 동적으로 설정하려는 가게 ID 값을 넣어주세요.
        model.addAttribute("menuId", menuId);
        return "showSelectMenu";
    }
    @PatchMapping("/{storeId}/patchMenu/{menuId}")
    public String patchMemu(@PathVariable Long storeId,
                            @PathVariable Long menuId,
                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                            @RequestBody MenuRequestDto menuRequestDto){
        String email = userDetails.getUser().getEmail();
        menuService.patchMenu(storeId,menuId,menuRequestDto,email);

        return "showMenuPage";
    }
    @DeleteMapping("/{storeId}/deleteMenu/{menuId}")
    public String deleteMenu(@PathVariable Long storeId,
                             @PathVariable Long menuId,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        String email = userDetails.getUser().getEmail();
        menuService.deleteMenu(storeId,menuId,email);
        return "showMenuPage";
    }
}
