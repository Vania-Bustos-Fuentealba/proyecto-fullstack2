package com.library.contentservice.controller;

import com.library.contentservice.dto.ContactRequestDTO;
import com.library.contentservice.dto.ContactResponseDTO;
import com.library.contentservice.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contact")
@RequiredArgsConstructor
@Tag(name = "Contacto", description = "Mensajes de contacto. Enviar es público; listar, leer y eliminar solo ADMIN")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @Operation(summary = "Enviar mensaje de contacto", description = "Registra un mensaje enviado desde el formulario de contacto. Acceso público")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mensaje registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ContactResponseDTO> createMessage(@Valid @RequestBody ContactRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createMessage(request));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Listar mensajes", description = "Retorna todos los mensajes recibidos, del más nuevo al más antiguo. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de mensajes"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<List<ContactResponseDTO>> getAllMessages() {
        return ResponseEntity.ok(contactService.getAllMessages());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Obtener mensaje por id", description = "Retorna el detalle de un mensaje. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<ContactResponseDTO> getMessageById(
            @Parameter(description = "Id del mensaje", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(contactService.getMessageById(id));
    }

    @PatchMapping("/{id}/read")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Marcar mensaje como leído", description = "Cambia el estado del mensaje a leído. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje marcado como leído"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<ContactResponseDTO> markAsRead(
            @Parameter(description = "Id del mensaje", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(contactService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar mensaje", description = "Elimina un mensaje. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mensaje eliminado"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    public ResponseEntity<Void> deleteMessage(
            @Parameter(description = "Id del mensaje", example = "1") @PathVariable Long id) {
        contactService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}