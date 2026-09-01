package com.library.reviewservice.service.impl;

import com.library.reviewservice.dto.ReviewRequestDTO;
import com.library.reviewservice.dto.ReviewResponseDTO;
import com.library.reviewservice.entity.Review;
import com.library.reviewservice.exceptions.ReviewNotFoundException;
import com.library.reviewservice.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewRequestDTO buildRequestDTO() {
        ReviewRequestDTO dto = new ReviewRequestDTO();
        dto.setUserId(1L);
        dto.setBookId(10L);
        dto.setComment("Muy buen libro");
        dto.setRating(5);
        return dto;
    }

    private Review buildReview(Long id, Long userId, Long bookId, String comment, Integer rating) {
        Review review = new Review();
        review.setId(id);
        review.setUserId(userId);
        review.setBookId(bookId);
        review.setComment(comment);
        review.setRating(rating);
        return review;
    }

    @Test
    @DisplayName("Debe crear una review correctamente")
    void shouldCreateReviewSuccessfully() {
        ReviewRequestDTO request = buildRequestDTO();
        Review saved = buildReview(1L, 1L, 10L, "Muy buen libro", 5);

        when(reviewRepository.existsByUserIdAndBookId(1L, 10L)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenReturn(saved);

        ReviewResponseDTO response = reviewService.createReview(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(10L, response.getBookId());
        assertEquals("Muy buen libro", response.getComment());
        assertEquals(5, response.getRating());

        verify(reviewRepository, times(1)).existsByUserIdAndBookId(1L, 10L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el usuario ya reseñó el libro")
    void shouldThrowExceptionWhenUserAlreadyReviewedBook() {
        ReviewRequestDTO request = buildRequestDTO();

        when(reviewRepository.existsByUserIdAndBookId(1L, 10L)).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewService.createReview(request)
        );

        assertEquals("El usuario ya dejó una reseña para este libro", exception.getMessage());
        verify(reviewRepository, times(1)).existsByUserIdAndBookId(1L, 10L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    @DisplayName("Debe listar reviews por libro")
    void shouldReturnReviewsByBook() {
        Review r1 = buildReview(1L, 1L, 10L, "Muy bueno", 5);
        Review r2 = buildReview(2L, 2L, 10L, "Entretenido", 4);

        when(reviewRepository.findByBookId(10L)).thenReturn(List.of(r1, r2));

        List<ReviewResponseDTO> response = reviewService.getReviewsByBook(10L);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Muy bueno", response.get(0).getComment());
        assertEquals("Entretenido", response.get(1).getComment());

        verify(reviewRepository, times(1)).findByBookId(10L);
    }

    @Test
    @DisplayName("Debe retornar lista vacía si un libro no tiene reviews")
    void shouldReturnEmptyListWhenBookHasNoReviews() {
        when(reviewRepository.findByBookId(99L)).thenReturn(List.of());

        List<ReviewResponseDTO> response = reviewService.getReviewsByBook(99L);

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(reviewRepository, times(1)).findByBookId(99L);
    }

    @Test
    @DisplayName("Debe listar reviews por usuario")
    void shouldReturnReviewsByUser() {
        Review r1 = buildReview(1L, 1L, 10L, "Muy bueno", 5);
        Review r2 = buildReview(2L, 1L, 11L, "Regular", 3);

        when(reviewRepository.findByUserId(1L)).thenReturn(List.of(r1, r2));

        List<ReviewResponseDTO> response = reviewService.getReviewsByUser(1L);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(10L, response.get(0).getBookId());
        assertEquals(11L, response.get(1).getBookId());

        verify(reviewRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("Debe eliminar una review correctamente")
    void shouldDeleteReviewSuccessfully() {
        Review review = buildReview(1L, 1L, 10L, "Muy buen libro", 5);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).delete(review);

        assertDoesNotThrow(() -> reviewService.deleteReview(1L));

        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).delete(review);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar una review inexistente")
    void shouldThrowExceptionWhenDeletingNonExistingReview() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        ReviewNotFoundException exception = assertThrows(
                ReviewNotFoundException.class,
                () -> reviewService.deleteReview(99L)
        );

        assertEquals("Review no encontrada con id 99", exception.getMessage());
        verify(reviewRepository, times(1)).findById(99L);
        verify(reviewRepository, never()).delete(any(Review.class));
    }
}