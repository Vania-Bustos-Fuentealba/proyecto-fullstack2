package com.library.inventoryservice.controller;

import com.library.inventoryservice.dto.InventoryRequestDTO;
import com.library.inventoryservice.dto.InventoryResponseDTO;
import com.library.inventoryservice.service.InventoryService;
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

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Operaciones de gestión de inventario de libros")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryService inventoryService;

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Obtener inventario por libro", description = "Retorna el inventario disponible de un libro específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"bookId\": 2, \"stock\": 15}"))),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado para ese libro"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<InventoryResponseDTO> getInventoryByBookId(
            @Parameter(description = "ID del libro a consultar", required = true)
            @PathVariable Long bookId) {
        log.info("Consultando inventario para bookId {}", bookId);
        return ResponseEntity.ok(inventoryService.getInventoryByBookId(bookId));
    }

    @PostMapping
    @Operation(summary = "Crear inventario", description = "Registra el inventario inicial de un libro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"bookId\": 2, \"stock\": 15}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o inventario ya existe para ese libro"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<InventoryResponseDTO> createInventory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del inventario a crear", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"bookId\": 2, \"stock\": 15}")))
            @Valid @RequestBody InventoryRequestDTO request) {
        log.info("Creando inventario para bookId {}", request.getBookId());
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(request));
    }
}