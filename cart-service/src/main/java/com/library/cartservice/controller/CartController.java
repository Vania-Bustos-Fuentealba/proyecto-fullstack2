// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.controller.
package com.library.cartservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.service.CartService;
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
@RequestMapping("/cart")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Cart", description = "Operaciones del carrito de compras")
// Nota de lectura: Declara el tipo CartController y su contrato.
public class CartController {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final CartService cartService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/health")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String health() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return "cart service funcionando correctamente";
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            summary = "Agregar item al carrito",
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            description = "Agrega un libro al carrito de un usuario"
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    )
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    responseCode = "201",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Item agregado correctamente",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = CartResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"quantity\": 1}"
                            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                            )
                    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                    )
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            ),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos o libro ya en carrito"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<CartResponseDTO> addItem(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Datos del item a agregar",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    required = true,
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"userId\": 1, \"bookId\": 2, \"quantity\": 1}"
                            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                            )
                    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                    )
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            )
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody CartRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Solicitud para agregar item al carrito, userId {}", request.getUserId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItem(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/{userId}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            summary = "Obtener carrito de un usuario",
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            description = "Retorna todos los items del carrito de un usuario específico"
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    )
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    responseCode = "200",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Lista de items del carrito",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"quantity\": 1}]"
                            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                            )
                    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                    )
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            ),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<List<CartResponseDTO>> getItemsByUser(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del usuario", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Consultando carrito del usuario {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(cartService.getItemsByUser(userId));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @DeleteMapping("/{id}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            summary = "Eliminar item del carrito",
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            description = "Elimina un item del carrito por su ID"
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    )
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "204", description = "Item eliminado correctamente"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Void> deleteCart(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del item a eliminar", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Eliminando item del carrito con id {}", id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartService.deleteCart(id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.noContent().build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
