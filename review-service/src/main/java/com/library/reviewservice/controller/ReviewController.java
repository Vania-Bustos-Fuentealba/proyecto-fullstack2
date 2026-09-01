package com.library.reviewservice.controller;

import com.library.reviewservice.dto.ReviewRequestDTO;
import com.library.reviewservice.dto.ReviewResponseDTO;
import com.library.reviewservice.service.ReviewService;
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
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Operaciones de gestión de reseñas de libros")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    public String health() {
        return "Review Service funcionando correctamente";
    }

    @PostMapping
    @Operation(summary = "Crear reseña", description = "Registra una nueva reseña de un libro por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o reseña duplicada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ReviewResponseDTO> createReview(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la reseña", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}")))
            @Valid @RequestBody ReviewRequestDTO request) {
        log.info("Creando review para bookId {}", request.getBookId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(request));
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Obtener reseñas por libro", description = "Retorna todas las reseñas de un libro específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas del libro",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}]"))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReviewResponseDTO>> getByBook(
            @Parameter(description = "ID del libro", required = true)
            @PathVariable Long bookId) {
        log.info("Consultando reviews del libro {}", bookId);
        return ResponseEntity.ok(reviewService.getReviewsByBook(bookId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener reseñas por usuario", description = "Retorna todas las reseñas hechas por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reseñas del usuario",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}]"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ReviewResponseDTO>> getByUser(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId) {
        log.info("Consultando reviews del usuario {}", userId);
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reseña eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "ID de la reseña a eliminar", required = true)
            @PathVariable Long id) {
        log.info("Eliminando review con id {}", id);
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}