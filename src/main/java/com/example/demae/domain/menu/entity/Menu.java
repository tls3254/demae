package com.example.demae.domain.menu.entity;

import com.example.demae.domain.menu.dto.MenuRequestDto;
import com.example.demae.domain.store.entity.Store;
import com.example.demae.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name ="store_id",nullable = false)
    private Store store;

    public Menu(MenuRequestDto menuRequestDto, Store store) {
        this.menuName = menuRequestDto.getMenuName();
        this.menuPrice = menuRequestDto.getMenuPrice();
        this.store = store;
    }

    public void update(MenuRequestDto menuRequestDto) {
        this.menuName = menuRequestDto.getMenuName();
        this.menuPrice = menuRequestDto.getMenuPrice();
    }
}
