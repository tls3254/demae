package com.example.demae.domain.cart.entity;

import com.example.demae.domain.user.entity.User;
import com.example.demae.domain.review.entity.Review;
import com.example.demae.global.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CartState cartState  = CartState.READY;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    public Cart(User user) {
        this.user = user;
    }

    public Cart(User user, int count) {
        this.user = user;
        this.totalPrice = count;
    }

    public void updateCount(int i) {
        this.totalPrice = i;
    }

    public void addTotalPrice(int orderItemPrice) {
        totalPrice += orderItemPrice;
    }

    public void updateCartState(CartState state) {
        this.cartState = state;
    }
}
