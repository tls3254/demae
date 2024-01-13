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

    public String createMenu(MenuRequestDto menuRequestDto,String email) {
        Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(()->new IllegalArgumentException("본인 가게가 아닙니다."));
        if(!store.getUser().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
        Menu menu = new Menu(menuRequestDto,store);
        menuRepository.save(menu);
        return "성공";
    }

    public List<MenuResponseDto> AllMenu(Long storeId) {
        List<Menu> storeCheck = menuRepository.findByStoreId(storeId);
        if(storeCheck.isEmpty()){
            throw new IllegalArgumentException("가게가 일치하지 않습니다.");
        }
        List<Menu> MenuList = menuRepository.findAll();
        List<Menu> newList = new ArrayList<>(MenuList);
        return  newList.stream().map(MenuResponseDto::new).toList();
    }
    public MenuResponseDto selectMenu(Long storeId,Long MenuId) {
        List<Menu> storeCheck = menuRepository.findByStoreId(storeId);
        if(storeCheck.isEmpty()){
            throw new IllegalArgumentException("가게가 일치하지 않습니다.");
        }
        Menu checkMenu = menuRepository.findById(MenuId).orElseThrow(()->new IllegalArgumentException("메뉴가 없습니다."));
        return new MenuResponseDto(checkMenu);
    }

    public void patchMenu(Long storeId,Long menuId,MenuRequestDto menuRequestDto,String email) {
        Store findStore = storeRepository.findByMenuListId(menuId);
        Menu menu = menuRepository.findById(menuId).orElseThrow(()->new IllegalArgumentException("메뉴가 없습니다."));
        if(!findStore.getUser().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
        menu.update(menuRequestDto);
    }

    public void deleteMenu(Long storeId,Long menuId,String email) {
        Store FindStore = storeRepository.findById(storeId).orElseThrow(()->new IllegalArgumentException("가게 정보가 없습니다"));
        Menu findMenu = menuRepository.findById(menuId).orElseThrow(()->new IllegalArgumentException("본인의 메뉴가 아닙니다."));
        if(!FindStore.getUser().getEmail().equals(email)){
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
        menuRepository.delete(findMenu);
    }


}
