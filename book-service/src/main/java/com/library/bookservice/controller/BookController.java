package com.library.bookservice.controller;

import com.library.bookservice.dto.BookRequestDTO;
import com.library.bookservice.dto.BookResponseDTO;
import com.library.bookservice.service.BookService;
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
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Operaciones de gestión de libros")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    public String health() {
        return "Book Service funcionando correctamente";
    }

    @PostMapping
    @Operation(summary = "Crear libro", description = "Registra un nuevo libro en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"title\": \"Clean Code\", \"author\": \"Robert Martin\", \"price\": 29.99, \"stock\": 10}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponseDTO> createBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del libro a crear", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"title\": \"Clean Code\", \"author\": \"Robert Martin\", \"price\": 29.99, \"stock\": 10}")))
            @Valid @RequestBody BookRequestDTO request) {
        log.info("Creando libro con título {}", request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @GetMapping
    @Operation(summary = "Listar libros", description = "Retorna todos los libros disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de libros",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"title\": \"Clean Code\", \"author\": \"Robert Martin\", \"price\": 29.99, \"stock\": 10}]"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        log.info("Listando todos los libros");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener libro por ID", description = "Retorna un libro específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"title\": \"Clean Code\", \"author\": \"Robert Martin\", \"price\": 29.99, \"stock\": 10}"))),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponseDTO> getBookById(
            @Parameter(description = "ID del libro", required = true)
            @PathVariable Long id) {
        log.info("Buscando libro con id {}", id);
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar libro", description = "Actualiza los datos de un libro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"title\": \"Clean Code 2\", \"author\": \"Robert Martin\", \"price\": 34.99, \"stock\": 5}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<BookResponseDTO> updateBook(
            @Parameter(description = "ID del libro a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del libro", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"title\": \"Clean Code 2\", \"author\": \"Robert Martin\", \"price\": 34.99, \"stock\": 5}")))
            @Valid @RequestBody BookRequestDTO request) {
        log.info("Actualizando libro con id {}", id);
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro", description = "Elimina un libro del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID del libro a eliminar", required = true)
            @PathVariable Long id) {
        log.info("Eliminando libro con id {}", id);
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}