package com.example.demae.domain.cart.repository;

import com.example.demae.domain.cart.entity.Cart;
import com.example.demae.domain.cart.entity.CartState;
import com.example.demae.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUser_UserId(Long userId);

    Cart findByReviews_ReviewId(Long reviewId);

    void deleteByUser(User findUser);

    Cart findByUser_UserIdAndCartState(Long userId, CartState cartState);

    Cart findByUser_UserIdAndCartId(Long userId, Long cartId);
}
