package com.example.demae.entity;

import com.example.demae.dto.menu.MenuRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "menu")
public class Menu {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Menu(MenuRequestDto menuRequestDto) {
        this.name = menuRequestDto.getName();
        this.price = menuRequestDto.getPrice();
    }
}
