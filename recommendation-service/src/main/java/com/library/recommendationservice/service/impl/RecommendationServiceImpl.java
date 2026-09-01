package com.library.recommendationservice.service.impl;

import com.library.recommendationservice.dto.RecommendationRequestDTO;
import com.library.recommendationservice.dto.RecommendationResponseDTO;
import com.library.recommendationservice.entity.Recommendation;
import com.library.recommendationservice.exceptions.RecommendationNotFoundException;
import com.library.recommendationservice.repository.RecommendationRepository;
import com.library.recommendationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger logger =
            LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final RecommendationRepository recommendationRepository;
    private final RestTemplate restTemplate;

    private RecommendationResponseDTO mapToResponse(Recommendation recommendation) {
        RecommendationResponseDTO response = new RecommendationResponseDTO();
        response.setId(recommendation.getId());
        response.setUserId(recommendation.getUserId());
        response.setBookId(recommendation.getBookId());
        response.setRecommendationText(recommendation.getRecommendationText());
        return response;
    }

    @Override
    public RecommendationResponseDTO createRecommendation(RecommendationRequestDTO request) {
        logger.info("Creando recomendación para userId {} sobre bookId {}", request.getUserId(), request.getBookId());

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

        Recommendation recommendation = new Recommendation();
        recommendation.setUserId(request.getUserId());
        recommendation.setBookId(request.getBookId());
        recommendation.setRecommendationText(request.getRecommendationText());

        Recommendation saved = recommendationRepository.save(recommendation);
        logger.info("Recomendación creada correctamente con id {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public List<RecommendationResponseDTO> getRecommendationsByUser(Long userId) {
        logger.info("Listando recomendaciones del userId {}", userId);
        return recommendationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RecommendationResponseDTO getRecommendationById(Long id) {
        logger.info("Buscando recomendación con id {}", id);

        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Recomendación con id {} no encontrada", id);
                    return new RecommendationNotFoundException("Recomendación no encontrada con id " + id);
                });

        return mapToResponse(recommendation);
    }

    @Override
    public void deleteRecommendation(Long id) {
        logger.info("Eliminando recomendación con id {}", id);

        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Recomendación con id {} no encontrada", id);
                    return new RecommendationNotFoundException("Recomendación no encontrada con id " + id);
                });

        recommendationRepository.delete(recommendation);
        logger.info("Recomendación con id {} eliminada correctamente", id);
    }
}
