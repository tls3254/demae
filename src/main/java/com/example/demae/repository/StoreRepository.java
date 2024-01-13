package com.example.demae.repository;

import com.example.demae.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByMenuListId(Long menuId);
}
