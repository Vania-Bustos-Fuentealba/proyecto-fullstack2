package com.library.notificationservice.controller;

import com.library.notificationservice.dto.NotificationRequestDTO;
import com.library.notificationservice.dto.NotificationResponseDTO;
import com.library.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Operaciones de gestión de notificaciones")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    public String health() {
        return "Notification Service funcionando correctamente";
    }

    @PostMapping
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación para un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\", \"status\": \"PENDING\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<NotificationResponseDTO> createNotification(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la notificación", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\"}")))
            @Valid @RequestBody NotificationRequestDTO request) {
        log.info("Creando notificación para userId {}", request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(request));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener notificaciones por usuario", description = "Retorna todas las notificaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\", \"status\": \"PENDING\"}]"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<NotificationResponseDTO>> getByUser(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId) {
        log.info("Consultando notificaciones del usuario {}", userId);
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID", description = "Retorna una notificación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\", \"status\": \"PENDING\"}"))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<NotificationResponseDTO> getById(
            @Parameter(description = "ID de la notificación", required = true)
            @PathVariable Long id) {
        log.info("Buscando notificación con id {}", id);
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }
}
