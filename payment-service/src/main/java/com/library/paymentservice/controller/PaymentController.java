package com.library.paymentservice.controller;

import com.library.paymentservice.dto.PaymentRequestDTO;
import com.library.paymentservice.dto.PaymentResponseDTO;
import com.library.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Operaciones de gestión de pagos")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Crear pago", description = "Registra un nuevo pago para una orden existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"orderId\": 1, \"amount\": 29.99, \"paymentMethod\": \"CREDIT_CARD\", \"status\": \"COMPLETED\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o pago ya existe para esa orden"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del pago a registrar", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"orderId\": 1, \"amount\": 29.99, \"paymentMethod\": \"CREDIT_CARD\", \"status\": \"COMPLETED\"}")))
            @Valid @RequestBody PaymentRequestDTO request) {
        log.info("Creando pago para orderId {}", request.getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(request));
    }
}