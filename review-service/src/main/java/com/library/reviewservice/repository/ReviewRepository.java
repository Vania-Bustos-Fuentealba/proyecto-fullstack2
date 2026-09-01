package com.library.reviewservice.repository;

import com.library.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    List<Review> findByBookId(Long bookId);

    List<Review> findByUserId(Long userId);
}