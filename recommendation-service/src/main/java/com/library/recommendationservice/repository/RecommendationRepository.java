package com.library.recommendationservice.repository;

import com.library.recommendationservice.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    List<Recommendation> findByUserId(Long userId);

    List<Recommendation> findByBookId(Long bookId);
}
