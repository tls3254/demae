package com.example.demae.repository;

import com.example.demae.entity.Sse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SseRepository extends JpaRepository<Sse, Long> {
	Optional<Sse> findByUserId(Long userId);
}
