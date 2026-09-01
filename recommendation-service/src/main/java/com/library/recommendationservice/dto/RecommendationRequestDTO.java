package com.library.recommendationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecommendationRequestDTO {
    @NotNull(message = "El userId es obligatorio")
    private Long userId;

    @NotNull(message = "El bookId es obligatorio")
    private Long bookId;

    @NotBlank(message = "El texto de recomendación es obligatorio")
    @Size(min = 2, max = 500, message = "El texto debe tener entre 2 y 500 caracteres")
    private String recommendationText;
}
