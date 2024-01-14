package com.example.demae.dto.menu;

import com.example.demae.entity.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuResponseDto {
    private Long id;
    private String name;
    private int price;
    private Long storeId;
    private List<String> pictureUrls;
  
    public MenuResponseDto(Menu menu,List<String> pictureUrls){
        this.id = menu.getId();
        this.name =menu.getName();
        this.price = menu.getPrice();
        this.storeId = menu.getStore().getId();
        this.pictureUrls = pictureUrls;
    }
}
