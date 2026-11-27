// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.controller.
package com.library.inventoryservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.service.InventoryService;
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

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestController
// Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
@RequestMapping("/inventory")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Inventory", description = "Operaciones de gestión de inventario de libros")
// Nota de lectura: Declara el tipo InventoryController y su contrato.
public class InventoryController {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final InventoryService inventoryService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/book/{bookId}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener inventario por libro", description = "Retorna el inventario disponible de un libro específico")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Inventario encontrado",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = InventoryResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"bookId\": 2, \"stock\": 15}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado para ese libro"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<InventoryResponseDTO> getInventoryByBookId(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del libro a consultar", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long bookId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Consultando inventario para bookId {}", bookId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(inventoryService.getInventoryByBookId(bookId));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Crear inventario", description = "Registra el inventario inicial de un libro")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "201", description = "Inventario creado correctamente",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = InventoryResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"bookId\": 2, \"stock\": 15}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos o inventario ya existe para ese libro"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<InventoryResponseDTO> createInventory(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Datos del inventario a crear", required = true,
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(examples = @ExampleObject(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            value = "{\"bookId\": 2, \"stock\": 15}")))
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody InventoryRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Creando inventario para bookId {}", request.getBookId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}