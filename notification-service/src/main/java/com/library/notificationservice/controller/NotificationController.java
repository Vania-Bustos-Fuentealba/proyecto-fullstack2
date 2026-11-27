// Nota de lectura: Ubica la clase dentro del paquete com.library.notificationservice.controller.
package com.library.notificationservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.dto.NotificationRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.dto.NotificationResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.service.NotificationService;
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
@RequestMapping("/notifications")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Notifications", description = "Operaciones de gestión de notificaciones")
// Nota de lectura: Declara el tipo NotificationController y su contrato.
public class NotificationController {

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final NotificationService notificationService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/health")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Health check", description = "Verifica que el servicio esté funcionando")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponse(responseCode = "200", description = "Servicio operativo")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public String health() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return "Notification Service funcionando correctamente";
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Crear notificación", description = "Registra una nueva notificación para un usuario")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "201", description = "Notificación creada correctamente",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = NotificationResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\", \"status\": \"PENDING\"}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<NotificationResponseDTO> createNotification(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    description = "Datos de la notificación", required = true,
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(examples = @ExampleObject(
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            value = "{\"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\"}")))
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Valid @RequestBody NotificationRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Creando notificación para userId {}", request.getUserId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/user/{userId}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener notificaciones por usuario", description = "Retorna todas las notificaciones de un usuario")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "[{\"id\": 1, \"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\", \"status\": \"PENDING\"}]"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<List<NotificationResponseDTO>> getByUser(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID del usuario", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Consultando notificaciones del usuario {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/{id}")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener notificación por ID", description = "Retorna una notificación específica por su ID")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses(value = {
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Notificación encontrada",
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    content = @Content(mediaType = "application/json",
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            schema = @Schema(implementation = NotificationResponseDTO.class),
                            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                            examples = @ExampleObject(
                                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                                    value = "{\"id\": 1, \"userId\": 1, \"message\": \"Su pedido fue procesado\", \"type\": \"EMAIL\", \"status\": \"PENDING\"}"))),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<NotificationResponseDTO> getById(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "ID de la notificación", required = true)
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @PathVariable Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Buscando notificación con id {}", id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
