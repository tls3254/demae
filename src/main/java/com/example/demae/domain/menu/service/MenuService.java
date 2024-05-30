package com.example.demae.domain.menu.service;

import com.example.demae.domain.menu.dto.MenuRequestDto;
import com.example.demae.domain.menu.dto.MenuResponseDto;
import com.example.demae.domain.menu.entity.Menu;
import com.example.demae.domain.menu.repository.MenuRepository;
import com.example.demae.domain.store.entity.Store;
import com.example.demae.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final StoreService storeService;
    private final MenuRepository menuRepository;

    public void createMenu(MenuRequestDto menuRequestDto, Long storeId, String email){
        Store store = storeService.findStore(storeId);
        checkStoreOwner(store, email);
        Menu menu = new Menu(menuRequestDto,store);
        menuRepository.save(menu);
    }

    public void patchMenu(Long storeId,Long menuId,MenuRequestDto menuRequestDto,String email) {
        Store store = storeService.findStore(storeId);
        Menu menu = findMenuAndStore(storeId,menuId);
        checkStoreOwner(store, email);
        menu.update(menuRequestDto);
    }

    public void deleteMenu(Long storeId,Long menuId,String email) {
        Store store = storeService.findStore(storeId);
        Menu menu = findMenuAndStore(storeId,menuId);
        checkStoreOwner(store, email);
        menuRepository.delete(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> findMenus(Long storeId) {
        List<Menu> menuCheck = menuRepository.findByStore_StoreId(storeId);
        List<MenuResponseDto> menuResponseDto = new ArrayList<>();
        for (Menu menu : menuCheck) {
            menuResponseDto.add(new MenuResponseDto(menu));
        }
        return menuResponseDto;
    }

    @Transactional(readOnly = true)
    public MenuResponseDto findMenuStore(Long storeId,Long menuId) {
        Menu menu = findMenuAndStore(storeId,menuId);
        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public Menu findMenuAndStore(Long storeId, Long menuId){
        return menuRepository.findByMenuIdAndStore_StoreId(menuId, storeId).orElseThrow(() -> new IllegalArgumentException("가게와 일치하는 메뉴가 없습니다."));
    }

    @Transactional(readOnly = true)
    public Menu findMenu(Long menuId){
        return menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));
    }

    private void checkStoreOwner(Store store, String email) {
        if (!store.getUser().getUserEmail().equals(email)) {
            throw new IllegalArgumentException("본인의 가게가 아닙니다.");
        }
    }
}

