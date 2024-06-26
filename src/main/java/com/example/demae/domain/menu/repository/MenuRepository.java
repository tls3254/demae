package com.example.demae.domain.menu.repository;

import com.example.demae.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findByStore_StoreId(Long storeId);

    Optional<Menu> findByMenuIdAndStore_StoreId(Long menuId, Long storeId);
}

