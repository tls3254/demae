package com.example.demae.service;

import com.example.demae.dto.menu.MenuRequestDto;
import com.example.demae.dto.menu.MenuResponseDto;
import com.example.demae.entity.Menu;
import com.example.demae.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
     private final MenuRepository menuRepository;

    public String createMenu(MenuRequestDto menuRequestDto) {
        Menu menu = new Menu(menuRequestDto);
        menuRepository.save(menu);
        return "성공";
    }

    public List<MenuResponseDto> AllMenu() {
        List<Menu> MenuList = menuRepository.findAll();
        List<Menu> newList = new ArrayList<>(MenuList);
        return  newList.stream().map(MenuResponseDto::new).toList();
    }
}
