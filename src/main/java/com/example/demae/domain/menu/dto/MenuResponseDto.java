package com.example.demae.domain.menu.dto;

import com.example.demae.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDto {
    private Long menuId;
    private String menuName;
    private int menuPrice;
    private Long storeId;
  
    public MenuResponseDto(Menu menu){
        this.menuId = menu.getMenuId();
        this.menuName =menu.getMenuName();
        this.menuPrice = menu.getMenuPrice();
        this.storeId = menu.getStore().getStoreId();
    }
}
