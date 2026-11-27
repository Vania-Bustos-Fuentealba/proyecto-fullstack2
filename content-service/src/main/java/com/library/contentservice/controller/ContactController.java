// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.controller.
package com.library.contentservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.dto.ContactRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.dto.ContactResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.service.ContactService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Operation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Parameter;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.responses.ApiResponse;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.responses.ApiResponses;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.tags.Tag;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Valid;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/v1/contact")
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Documenta la operación para Swagger/OpenAPI.
@Tag(name = "Contacto", description = "Mensajes de contacto. Enviar es público; listar, leer y eliminar solo ADMIN")
// Nota de lectura: Declara el tipo ContactController y su contrato.
public class ContactController {

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final ContactService contactService;

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PostMapping
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Enviar mensaje de contacto", description = "Registra un mensaje enviado desde el formulario de contacto. Acceso público")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses({
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "201", description = "Mensaje registrado correctamente"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<ContactResponseDTO> createMessage(@Valid @RequestBody ContactRequestDTO request) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createMessage(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @SecurityRequirement(name = "bearerAuth")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Listar mensajes", description = "Retorna todos los mensajes recibidos, del más nuevo al más antiguo. Requiere rol ADMIN")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses({
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Lista de mensajes"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<List<ContactResponseDTO>> getAllMessages() {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(contactService.getAllMessages());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/{id}")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @SecurityRequirement(name = "bearerAuth")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Obtener mensaje por id", description = "Retorna el detalle de un mensaje. Requiere rol ADMIN")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses({
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Mensaje encontrado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<ContactResponseDTO> getMessageById(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "Id del mensaje", example = "1") @PathVariable Long id) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(contactService.getMessageById(id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @PatchMapping("/{id}/read")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @SecurityRequirement(name = "bearerAuth")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Marcar mensaje como leído", description = "Cambia el estado del mensaje a leído. Requiere rol ADMIN")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses({
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "200", description = "Mensaje marcado como leído"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<ContactResponseDTO> markAsRead(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "Id del mensaje", example = "1") @PathVariable Long id) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.ok(contactService.markAsRead(id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @DeleteMapping("/{id}")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @SecurityRequirement(name = "bearerAuth")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Eliminar mensaje", description = "Elimina un mensaje. Requiere rol ADMIN")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ApiResponses({
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "204", description = "Mensaje eliminado"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    })
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ResponseEntity<Void> deleteMessage(
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @Parameter(description = "Id del mensaje", example = "1") @PathVariable Long id) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        contactService.deleteMessage(id);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ResponseEntity.noContent().build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}