package com.library.recommendationservice.controller;

import com.library.recommendationservice.dto.RecommendationRequestDTO;
import com.library.recommendationservice.dto.RecommendationResponseDTO;
import com.library.recommendationservice.service.RecommendationService;
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
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Tag(name = "Recommendations", description = "Operaciones de gestión de recomendaciones de libros")
public class RecommendationController {

    private static final Logger log = LoggerFactory.getLogger(RecommendationController.class);
    private final RecommendationService recommendationService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    public String health() {
        return "Recommendation Service funcionando correctamente";
    }

    @PostMapping
    @Operation(summary = "Crear recomendación", description = "Registra una nueva recomendación de libro para un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recomendación creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecommendationResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"recommendationText\": \"Excelente libro de programación\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RecommendationResponseDTO> createRecommendation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la recomendación", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"userId\": 1, \"bookId\": 2, \"recommendationText\": \"Excelente libro de programación\"}")))
            @Valid @RequestBody RecommendationRequestDTO request) {
        log.info("Creando recomendación para userId {}", request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationService.createRecommendation(request));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener recomendaciones por usuario", description = "Retorna todas las recomendaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de recomendaciones",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"recommendationText\": \"Excelente libro de programación\"}]"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<RecommendationResponseDTO>> getByUser(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId) {
        log.info("Consultando recomendaciones del usuario {}", userId);
        return ResponseEntity.ok(recommendationService.getRecommendationsByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener recomendación por ID", description = "Retorna una recomendación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecommendationResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"recommendationText\": \"Excelente libro de programación\"}"))),
            @ApiResponse(responseCode = "404", description = "Recomendación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<RecommendationResponseDTO> getById(
            @Parameter(description = "ID de la recomendación", required = true)
            @PathVariable Long id) {
        log.info("Buscando recomendación con id {}", id);
        return ResponseEntity.ok(recommendationService.getRecommendationById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar recomendación", description = "Elimina una recomendación por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recomendación eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Recomendación no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteRecommendation(
            @Parameter(description = "ID de la recomendación a eliminar", required = true)
            @PathVariable Long id) {
        log.info("Eliminando recomendación con id {}", id);
        recommendationService.deleteRecommendation(id);
        return ResponseEntity.noContent().build();
    }
}