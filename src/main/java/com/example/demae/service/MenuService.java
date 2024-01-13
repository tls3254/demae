package com.example.demae.service;

import com.example.demae.dto.menu.MenuRequestDto;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.entity.Menu;
import com.example.demae.entity.Store;
import com.example.demae.entity.User;
import com.example.demae.repository.MenuRepository;
import com.example.demae.repository.StoreRepository;
import com.example.demae.repository.UserRepository;
import com.example.demae.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
     private final MenuRepository menuRepository;
     private final StoreRepository storeRepository;
     private final UserRepository userRepository;

    public String createMenu(Long storeId,MenuRequestDto menuRequestDto,String email) {
        Store store = storeRepository.findById(storeId).orElseThrow(()->new IllegalArgumentException("본인 가게가 아닙니다."));
        Menu menu = new Menu(menuRequestDto,store);
        menuRepository.save(menu);
        return "성공";
    }

    public List<MenuResponseDto> AllMenu() {
        List<Menu> MenuList = menuRepository.findAll();
        List<Menu> newList = new ArrayList<>(MenuList);
        return  newList.stream().map(MenuResponseDto::new).toList();
    }

    public void patchMenu(Long storeId,Long menuId,MenuRequestDto menuRequestDto,String email) {
        Store findMenu = storeRepository.findByMenuListId(menuId);
        storeRepository.findById(storeId).orElseThrow(()->new IllegalArgumentException("본인 가게가 아닙니다."));
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        if(!findMenu.getUser().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
        menu.update(menuRequestDto);
    }

    public void deleteMenu(MenuRequestDto menuRequestDto) {
    }
}
