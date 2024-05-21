package com.example.demae.domain.menu.controller;

import com.example.demae.domain.menu.dto.MenuResponseDto;
import com.example.demae.domain.menu.service.MenuService;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}/menus")
public class MenuControllerPage {

    private final MenuService menuService;
    private final UserService userService;

    @GetMapping("/join")
    public String menuCreatePage(Model model, @PathVariable Long storeId){
        model.addAttribute("storeId", storeId);
        return "menu/menu";
    }

    @GetMapping
    public String findMenus(@PathVariable Long storeId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model){
        User user = userService.findUser(userDetails.getUsername());
        List<MenuResponseDto> findMenus = menuService.findMenus(storeId);
        model.addAttribute("menuList", findMenus);
        if (user.getUserRole().name().equals("STORE") && user.getStore().getStoreId().equals(storeId)) {
            return "menu/showMenuPage";
        }
        return "menu/showMenuPageUser";
    }

    @GetMapping("/{menuId}")
    public String findMenu(@PathVariable Long storeId,
                           @PathVariable Long menuId,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model){
        User user = userService.findUser(userDetails.getUsername());
        MenuResponseDto findMenu = menuService.findMenu(storeId,menuId);
        model.addAttribute("menuOne", findMenu);
        if (user.getUserRole().name().equals("STORE") && user.getStore().getStoreId().equals(storeId)) {
            return "menu/showSelectMenu";
        }
        return "menu/showSelectMenuUser";
    }
}
