package com.library.reviewservice.service.impl;

import com.library.reviewservice.dto.ReviewRequestDTO;
import com.library.reviewservice.dto.ReviewResponseDTO;
import com.library.reviewservice.entity.Review;
import com.library.reviewservice.exceptions.ReviewNotFoundException;
import com.library.reviewservice.repository.ReviewRepository;
import com.library.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger =
            LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final RestTemplate restTemplate;

    private ReviewResponseDTO mapToResponse(Review review) {
        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(review.getId());
        response.setUserId(review.getUserId());
        response.setBookId(review.getBookId());
        response.setComment(review.getComment());
        response.setRating(review.getRating());
        return response;
    }

    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO request) {
        logger.info("Creando review del userId {} para bookId {}", request.getUserId(), request.getBookId());

        String responseUser = restTemplate.getForObject(
                "http://user-service:8080/users/" + request.getUserId(),
                String.class
        );
        logger.info("Respuesta desde user-service: {}", responseUser);

        String responseBook = restTemplate.getForObject(
                "http://book-service:8080/books/" + request.getBookId(),
                String.class
        );
        logger.info("Respuesta desde book-service: {}", responseBook);

        if (reviewRepository.existsByUserIdAndBookId(request.getUserId(), request.getBookId())) {
            throw new RuntimeException("El usuario ya dejó una reseña para este libro");
        }

        Review review = new Review();
        review.setUserId(request.getUserId());
        review.setBookId(request.getBookId());
        review.setComment(request.getComment());
        review.setRating(request.getRating());

        Review saved = reviewRepository.save(review);
        logger.info("Review creada correctamente con id {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByBook(Long bookId) {
        logger.info("Listando reviews del bookId {}", bookId);
        return reviewRepository.findByBookId(bookId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        logger.info("Listando reviews del userId {}", userId);
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteReview(Long id) {
        logger.info("Eliminando review con id {}", id);

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Review con id {} no encontrada", id);
                    return new ReviewNotFoundException("Review no encontrada con id " + id);
                });

        reviewRepository.delete(review);
        logger.info("Review con id {} eliminada correctamente", id);
    }
}