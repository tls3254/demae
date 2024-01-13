package com.example.demae.repository;

import com.example.demae.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {
    List<Menu> findByStoreId(Long storeId);
}
