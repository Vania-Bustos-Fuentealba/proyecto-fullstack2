package com.library.orderservice.controller;

import com.library.orderservice.dto.OrderRequestDTO;
import com.library.orderservice.dto.OrderResponseDTO;
import com.library.orderservice.service.OrderService;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Operaciones de gestión de órdenes de compra")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Crear orden", description = "Registra una nueva orden de compra para un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"total\": 29.99, \"status\": \"PENDING\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<OrderResponseDTO> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la orden a crear", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"userId\": 1, \"bookId\": 2}")))
            @Valid @RequestBody OrderRequestDTO request) {
        log.info("Creando orden para userId {} y bookId {}", request.getUserId(), request.getBookId());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request));
    }
}
