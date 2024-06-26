package com.example.demae.domain.user.repository;

import com.example.demae.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserEmail(String email);

    Optional<User> findByRefreshToken(String s);
}
