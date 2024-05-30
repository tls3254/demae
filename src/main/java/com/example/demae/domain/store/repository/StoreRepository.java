package com.example.demae.domain.store.repository;

import com.example.demae.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

	Optional<Store> findByUser_UserId(Long userId);
	List<Store> findByStoreCategory(String category);
	Page<Store> findAll(Pageable pageable);
}
