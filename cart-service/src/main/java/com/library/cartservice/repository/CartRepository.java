package com.library.cartservice.repository;

import com.library.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUserId(Long userId);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    Optional<Cart> findByUserIdAndBookId(Long userId, Long bookId);
}
