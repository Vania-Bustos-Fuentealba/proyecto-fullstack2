package com.library.cartservice.controller;

import com.library.cartservice.dto.CartRequestDTO;
import com.library.cartservice.dto.CartResponseDTO;
import com.library.cartservice.service.CartService;
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
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Operaciones del carrito de compras")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    public String health() {
        return "cart service funcionando correctamente";
    }

    @PostMapping
    @Operation(
            summary = "Agregar item al carrito",
            description = "Agrega un libro al carrito de un usuario"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Item agregado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"quantity\": 1}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o libro ya en carrito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<CartResponseDTO> addItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del item a agregar",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = "{\"userId\": 1, \"bookId\": 2, \"quantity\": 1}"
                            )
                    )
            )
            @Valid @RequestBody CartRequestDTO request) {
        log.info("Solicitud para agregar item al carrito, userId {}", request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addItem(request));
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Obtener carrito de un usuario",
            description = "Retorna todos los items del carrito de un usuario específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de items del carrito",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"userId\": 1, \"bookId\": 2, \"quantity\": 1}]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<CartResponseDTO>> getItemsByUser(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long userId) {
        log.info("Consultando carrito del usuario {}", userId);
        return ResponseEntity.ok(cartService.getItemsByUser(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar item del carrito",
            description = "Elimina un item del carrito por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Item no encontrado")
    })
    public ResponseEntity<Void> deleteCart(
            @Parameter(description = "ID del item a eliminar", required = true)
            @PathVariable Long id) {
        log.info("Eliminando item del carrito con id {}", id);
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
