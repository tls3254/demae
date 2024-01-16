package com.example.demae.repository;

import com.example.demae.entity.Cart;
import com.example.demae.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
	List<Cart> findByUserId(Long userId);
	void deleteByUser(User user);
}
