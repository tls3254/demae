package com.example.demae.dto.menu;

import com.example.demae.entity.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponseDto {
    private String name;
    private int price;
  
    public MenuResponseDto(Menu menu){
        this.name =menu.getName();
        this.price = menu.getPrice();
    }
}
