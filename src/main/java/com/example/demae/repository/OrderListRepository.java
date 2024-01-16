package com.example.demae.repository;

import com.example.demae.entity.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderListRepository extends JpaRepository<OrderList, Long> {
	List<OrderList> findByOrderId(Long id);
	List<OrderList> findByStoreId(Long storeId);
}
