package com.example.demae.entity;

import com.example.demae.dto.store.StoreRequestDto;
import com.example.demae.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "store") // "store"는 Menu 엔터티에 있는 Store 타입 필드의 이름
    private List<Menu> menus = new ArrayList<>();


    public Store(StoreRequestDto storeRequestDto) {
        this.name = storeRequestDto.getName();
        this.address = storeRequestDto.getAddress();
        this.category = storeRequestDto.getCategory();
    }
}
