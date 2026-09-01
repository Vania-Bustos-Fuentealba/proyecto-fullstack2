package com.library.cartservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequestDTO {
    @NotNull(message = "El userId es obligatorio")
    private Long userId;
    @NotNull(message = "El bookId es obligatorio")
    private Long bookId;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;

}
