package com.library.userservice.controller;

import com.library.userservice.dto.UserRequestDTO;
import com.library.userservice.dto.UserResponseDTO;
import com.library.userservice.service.UserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operaciones de gestión de usuarios")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserResponseDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a crear", required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}")))
            @Valid @RequestBody UserRequestDTO request) {
        log.info("Creando usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}]"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Listando usuarios");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}"))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Long id) {
        log.info("Buscando usuario con id {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable Long id) {
        log.info("Eliminando usuario con id {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        log.info("Eliminando usuario con email {}", email);
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }
}