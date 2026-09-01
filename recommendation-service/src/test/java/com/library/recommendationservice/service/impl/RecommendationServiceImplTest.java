package com.library.recommendationservice.service.impl;

import com.library.recommendationservice.dto.RecommendationRequestDTO;
import com.library.recommendationservice.dto.RecommendationResponseDTO;
import com.library.recommendationservice.entity.Recommendation;
import com.library.recommendationservice.exceptions.RecommendationNotFoundException;
import com.library.recommendationservice.repository.RecommendationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RecommendationServiceImplTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    private RecommendationRequestDTO buildRequestDTO() {
        RecommendationRequestDTO dto = new RecommendationRequestDTO();
        dto.setUserId(1L);
        dto.setBookId(10L);
        dto.setRecommendationText("Te recomiendo este libro por su narrativa");
        return dto;
    }

    private Recommendation buildRecommendation(Long id, Long userId, Long bookId, String text) {
        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        recommendation.setUserId(userId);
        recommendation.setBookId(bookId);
        recommendation.setRecommendationText(text);
        return recommendation;
    }

    @Test
    @DisplayName("Debe crear una recomendación correctamente")
    void shouldCreateRecommendationSuccessfully() {
        RecommendationRequestDTO request = buildRequestDTO();
        Recommendation saved = buildRecommendation(
                1L, 1L, 10L, "Te recomiendo este libro por su narrativa"
        );

        when(restTemplate.getForObject("http://localhost:8082/books/10", String.class))
                .thenReturn("{\"id\":10,\"title\":\"Libro de prueba\"}");
        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(saved);

        RecommendationResponseDTO response = recommendationService.createRecommendation(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(10L, response.getBookId());
        assertEquals("Te recomiendo este libro por su narrativa", response.getRecommendationText());

        verify(restTemplate, times(1))
                .getForObject("http://localhost:8082/books/10", String.class);
        verify(recommendationRepository, times(1)).save(any(Recommendation.class));
    }

    @Test
    @DisplayName("Debe listar recomendaciones por usuario")
    void shouldReturnRecommendationsByUser() {
        Recommendation r1 = buildRecommendation(1L, 1L, 10L, "Recomendación 1");
        Recommendation r2 = buildRecommendation(2L, 1L, 11L, "Recomendación 2");

        when(recommendationRepository.findByUserId(1L)).thenReturn(List.of(r1, r2));

        List<RecommendationResponseDTO> response = recommendationService.getRecommendationsByUser(1L);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Recomendación 1", response.get(0).getRecommendationText());
        assertEquals("Recomendación 2", response.get(1).getRecommendationText());

        verify(recommendationRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("Debe retornar lista vacía si el usuario no tiene recomendaciones")
    void shouldReturnEmptyListWhenUserHasNoRecommendations() {
        when(recommendationRepository.findByUserId(99L)).thenReturn(List.of());

        List<RecommendationResponseDTO> response = recommendationService.getRecommendationsByUser(99L);

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(recommendationRepository, times(1)).findByUserId(99L);
    }

    @Test
    @DisplayName("Debe obtener una recomendación por id")
    void shouldReturnRecommendationById() {
        Recommendation recommendation = buildRecommendation(1L, 1L, 10L, "Texto recomendado");

        when(recommendationRepository.findById(1L)).thenReturn(Optional.of(recommendation));

        RecommendationResponseDTO response = recommendationService.getRecommendationById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(10L, response.getBookId());
        assertEquals("Texto recomendado", response.getRecommendationText());

        verify(recommendationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la recomendación no existe")
    void shouldThrowExceptionWhenRecommendationNotFound() {
        when(recommendationRepository.findById(99L)).thenReturn(Optional.empty());

        RecommendationNotFoundException exception = assertThrows(
                RecommendationNotFoundException.class,
                () -> recommendationService.getRecommendationById(99L)
        );

        assertEquals("Recomendación no encontrada con id 99", exception.getMessage());
        verify(recommendationRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe eliminar una recomendación correctamente")
    void shouldDeleteRecommendationSuccessfully() {
        Recommendation recommendation = buildRecommendation(1L, 1L, 10L, "Texto recomendado");

        when(recommendationRepository.findById(1L)).thenReturn(Optional.of(recommendation));
        doNothing().when(recommendationRepository).delete(recommendation);

        assertDoesNotThrow(() -> recommendationService.deleteRecommendation(1L));

        verify(recommendationRepository, times(1)).findById(1L);
        verify(recommendationRepository, times(1)).delete(recommendation);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar una recomendación inexistente")
    void shouldThrowExceptionWhenDeletingNonExistingRecommendation() {
        when(recommendationRepository.findById(99L)).thenReturn(Optional.empty());

        RecommendationNotFoundException exception = assertThrows(
                RecommendationNotFoundException.class,
                () -> recommendationService.deleteRecommendation(99L)
        );

        assertEquals("Recomendación no encontrada con id 99", exception.getMessage());
        verify(recommendationRepository, times(1)).findById(99L);
        verify(recommendationRepository, never()).delete(any(Recommendation.class));
    }
}