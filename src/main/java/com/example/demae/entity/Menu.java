package com.example.demae.entity;

import com.example.demae.dto.menu.MenuRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name ="store_id",nullable = false)
    private Store store;

    public Menu(MenuRequestDto menuRequestDto,Store store) {
        this.name = menuRequestDto.getName();
        this.price = menuRequestDto.getPrice();
        this.store = store;
    }
    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
