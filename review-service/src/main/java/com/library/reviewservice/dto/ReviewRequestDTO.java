package com.library.reviewservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReviewRequestDTO {
    @NotNull(message = "El userId es obligatorio")
    private Long userId;

    @NotNull(message = "El bookId es obligatorio")
    private Long bookId;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(min = 2, max = 500, message = "El comentario debe tener entre 2 y 500 caracteres")
    private String comment;

    @NotNull(message = "El rating es obligatorio")
    @Min(value = 1, message = "El rating mínimo es 1")
    @Max(value = 5, message = "El rating máximo es 5")
    private Integer rating;
}
