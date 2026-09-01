package com.library.reviewservice.service;

import com.library.reviewservice.dto.ReviewRequestDTO;
import com.library.reviewservice.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ReviewResponseDTO createReview(ReviewRequestDTO request);

    List<ReviewResponseDTO> getReviewsByBook(Long bookId);

    List<ReviewResponseDTO> getReviewsByUser(Long userId);

    void deleteReview(Long id);
}