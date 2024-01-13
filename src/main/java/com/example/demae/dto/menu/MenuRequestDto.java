package com.example.demae.dto.menu;

import com.example.demae.entity.Menu;
import com.example.demae.entity.Store;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDto {
    private String name;
    private int price;
}
