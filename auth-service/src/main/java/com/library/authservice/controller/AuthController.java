package com.library.authservice.controller;

import com.library.authservice.dto.LoginRequestDTO;
import com.library.authservice.dto.LoginResponseDTO;
import com.library.authservice.dto.RegisterRequestDTO;
import com.library.authservice.dto.UserResponseDTO;
import com.library.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operaciones de autenticación y registro de usuarios")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class),
                            examples = @ExampleObject(value = "{\"token\": \"eyJhbGciOiJIUzI1NiJ9...\"}"))),
            @ApiResponse(responseCode = "400", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoginResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales del usuario",
                    required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"email\": \"usuario@email.com\", \"password\": \"123456\"}")))
            @Valid @RequestBody LoginRequestDTO request) {
        log.info("Intentando login para email {}", request.getEmail());
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\"}"))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<UserResponseDTO> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario",
                    required = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"password\": \"123456\"}")))
            @Valid @RequestBody RegisterRequestDTO request) {
        log.info("Registrando nuevo usuario con email {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(request));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario desde Auth Service y su perfil asociado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Eliminando usuario desde auth-service con id {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}