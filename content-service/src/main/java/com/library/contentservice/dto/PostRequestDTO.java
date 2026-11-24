package com.library.contentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    private String title;

    @NotBlank(message = "El resumen es obligatorio")
    @Size(max = 300, message = "El resumen no puede superar los 300 caracteres")
    private String summary;

    @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres")
    private String imageUrl;

    @Size(max = 150, message = "El texto alternativo no puede superar los 150 caracteres")
    private String imageAlt;

    @NotBlank(message = "El contenido es obligatorio")
    private String content;
}