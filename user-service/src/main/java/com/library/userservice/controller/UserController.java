// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.controller.
package com.library.userservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.service.UserService;
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
@RequestMapping("/users")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Users", description = "Operaciones de gestión de usuarios")
// Nota de lectura: Declara el tipo UserController y su contrato.
public class UserController {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final UserService userService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario en el sistema")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = UserResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos o correo ya registrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<UserResponseDTO> createUser(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Datos del usuario a crear", required = true,
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(examples = @ExampleObject(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            value = "{\"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}")))
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody UserRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Creando usuario");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios registrados")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Lista de usuarios",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "[{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}]"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Listando usuarios");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(userService.getAllUsers());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/{id}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico por su ID")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = UserResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"name\": \"Juan Pérez\", \"email\": \"juan@email.com\", \"phone\": \"912345678\", \"address\": \"Av. Principal 123\"}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<UserResponseDTO> getUserById(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del usuario", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Buscando usuario con id {}", id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(userService.getUserById(id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @DeleteMapping("/{id}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema por su ID")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Void> deleteUser(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del usuario a eliminar", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Eliminando usuario con id {}", id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        userService.deleteUser(id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.noContent().build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @DeleteMapping("/email/{email}")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Eliminando usuario con email {}", email);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        userService.deleteUserByEmail(email);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.noContent().build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PutMapping("/email/{email}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Sincronizar identidad del perfil desde Auth")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<UserResponseDTO> updateIdentity(@PathVariable String email,
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody com.library.userservice.dto.IdentityUpdateDTO request) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(userService.updateIdentity(email, request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
