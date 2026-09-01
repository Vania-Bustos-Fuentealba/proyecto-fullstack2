package com.library.bookservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 2, max = 150, message = "El título debe tener entre 2 y 150 caracteres")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    @Size(min = 2, max = 100, message = "El autor debe tener entre 2 y 100 caracteres")
    private String author;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double price;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
