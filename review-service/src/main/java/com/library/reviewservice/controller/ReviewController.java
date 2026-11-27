// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.controller.
package com.library.reviewservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.service.ReviewService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Operation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Parameter;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.media.Content;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.media.ExampleObject;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.media.Schema;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.responses.ApiResponse;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.tags.Tag;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Valid;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.*;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestController
// Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
@RequestMapping("/reviews")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Reviews", description = "Operaciones de gestión de reseñas de libros")
// Nota de lectura: Declara el tipo ReviewController y su contrato.
public class ReviewController {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final ReviewService reviewService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/health")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String health() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return "Review Service funcionando correctamente";
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Crear reseña", description = "Registra una nueva reseña de un libro por un usuario")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "201", description = "Reseña creada correctamente",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = ReviewResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos o reseña duplicada"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<ReviewResponseDTO> createReview(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Datos de la reseña", required = true,
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(examples = @ExampleObject(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            value = "{\"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}")))
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody ReviewRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Creando review para bookId {}", request.getBookId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/book/{bookId}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener reseñas por libro", description = "Retorna todas las reseñas de un libro específico")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Lista de reseñas del libro",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}]"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<List<ReviewResponseDTO>> getByBook(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del libro", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long bookId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Consultando reviews del libro {}", bookId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(reviewService.getReviewsByBook(bookId));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/user/{userId}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener reseñas por usuario", description = "Retorna todas las reseñas hechas por un usuario")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Lista de reseñas del usuario",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"comment\": \"Excelente libro\", \"rating\": 5}]"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<List<ReviewResponseDTO>> getByUser(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del usuario", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Consultando reviews del usuario {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @DeleteMapping("/{id}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña por su ID")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "204", description = "Reseña eliminada correctamente"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Void> deleteReview(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID de la reseña a eliminar", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Eliminando review con id {}", id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        reviewService.deleteReview(id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.noContent().build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}