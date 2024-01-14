package com.example.demae.repository;

import com.example.demae.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
	Optional<Store> findByName(String name);
	List<Store> findByCategory(String category);
	Page<Store> findAll(Pageable pageable);
  	Store findByMenusId(Long menuId);
}
