package com.example.demae.domain.store.entity;

import com.example.demae.domain.store.dto.StoreRequestDto;
import com.example.demae.domain.store.dto.StoreUpdateRequestDto;
import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.menu.entity.Menu;
import com.example.demae.global.audit.Auditable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String storeAddress;

    @Column(nullable = false)
    private String storeCategory;
  
    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Store(StoreRequestDto storeRequestDto, User user) {
        this.storeName = storeRequestDto.getStoreName();
        this.storeAddress = storeRequestDto.getStoreAddress();
        this.storeCategory = storeRequestDto.getStoreCategory();
        this.user = user;
    }

    public void update(StoreUpdateRequestDto storeRequestDto) {
        this.storeName = storeRequestDto.getStoreName();
        this.storeAddress = storeRequestDto.getStoreAddress();
        this.storeCategory = storeRequestDto.getStoreCategory();
    }
}
