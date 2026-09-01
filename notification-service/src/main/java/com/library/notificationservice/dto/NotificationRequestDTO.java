package com.library.notificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotificationRequestDTO {
    @NotNull(message = "El userId es obligatorio")
    private Long userId;

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min = 2, max = 255, message = "El mensaje debe tener entre 2 y 255 caracteres")
    private String message;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    private String type;
}
