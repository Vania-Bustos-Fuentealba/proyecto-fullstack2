package com.library.paymentservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "El orderId es obligatorio")
    private Long orderId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private Double amount;

    @NotBlank(message = "El método de pago es obligatorio")
    private String paymentMethod;

    @NotBlank(message = "El estado es obligatorio")
    private String status;
}
