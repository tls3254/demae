package com.example.demae.dto.menu;

import com.example.demae.entity.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private Long storeId;
  
    public MenuResponseDto(Menu menu){
        this.id = menu.getId();
        this.name =menu.getName();
        this.price = menu.getPrice();
        this.storeId = menu.getStore().getId();
    }
}
