package com.library.recommendationservice.service;

import com.library.recommendationservice.dto.RecommendationRequestDTO;
import com.library.recommendationservice.dto.RecommendationResponseDTO;

import java.util.List;

public interface RecommendationService {
    RecommendationResponseDTO createRecommendation(RecommendationRequestDTO request);
    List<RecommendationResponseDTO> getRecommendationsByUser(Long userId);

    RecommendationResponseDTO getRecommendationById(Long id);

    void deleteRecommendation(Long id);
}
