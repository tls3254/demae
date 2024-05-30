package com.example.demae.domain.store.controller;

import com.example.demae.domain.store.dto.StoreResponseDto;
import com.example.demae.domain.store.service.StoreService;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreControllerPage {

    private final StoreService storeService;
    private final UserService userService;

    @GetMapping("/join")
    public String home(){return "store/store";}

    @GetMapping
    public String findStores(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Model model){
        Page<StoreResponseDto> stores = storeService.findStoreAll(page, size);
        model.addAttribute("storePages", stores);
        return "store/showAllStorePage";
    }

    @GetMapping("/category")
    public String findByCategory(@RequestParam String category,
                                 Model model){
        List<StoreResponseDto> stores = storeService.findByCategory(category);
        model.addAttribute("storeList", stores);
        return "store/showStorePage";
    }

    @GetMapping("/{storeId}")
    public String findStore(@PathVariable Long storeId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model){
        User user = userService.findUser(userDetails.getUsername());
        StoreResponseDto store = storeService.findStoreOne(storeId);
        model.addAttribute("store", store);
        if (userDetails.getAuthorities().toString().equals("ROLE_ADMIN") && user.getStore().getStoreId().equals(storeId)) {
            return "store/showStorePage";
        }
        return "store/showStorePageUser";
    }

}

