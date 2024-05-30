package com.example.demae.domain.menu.controller;

import com.example.demae.domain.menu.dto.MenuRequestDto;
import com.example.demae.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class MenuController {

	private final MenuService menuService;

    @PostMapping("/{storeId}/menus")
    public ResponseEntity<String> createMenu(@PathVariable Long storeId,
                                             @RequestBody MenuRequestDto menuRequestDto,
                                             @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        menuService.createMenu(menuRequestDto, storeId, email);
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/{storeId}/patchMenu/{menuId}")
    public ResponseEntity<String> patchMemu(@PathVariable Long storeId,
                                            @PathVariable Long menuId,
                                            @RequestBody MenuRequestDto menuRequestDto,
                                            @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        menuService.patchMenu(storeId,menuId,menuRequestDto,email);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{storeId}/deleteMenu/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long storeId,
                                             @PathVariable Long menuId,
                                             @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        menuService.deleteMenu(storeId,menuId,email);
        return ResponseEntity.ok("ok");
    }
}
