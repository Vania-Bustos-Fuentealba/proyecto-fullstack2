package com.library.recommendationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponseDTO {
    private Long id;
    private Long userId;
    private Long bookId;
    private String recommendationText;

}
