package com.library.contentservice.controller;

import com.library.contentservice.dto.PostRequestDTO;
import com.library.contentservice.dto.PostResponseDTO;
import com.library.contentservice.service.PostService;
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
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Operaciones del blog. Lectura pública; crear, editar y eliminar solo ADMIN")
public class PostController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "Listar posts", description = "Retorna todos los posts, del más nuevo al más antiguo. Acceso público")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de posts"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener post por id", description = "Retorna el detalle de un post. Acceso público")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post encontrado"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    public ResponseEntity<PostResponseDTO> getPostById(
            @Parameter(description = "Id del post", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Crear post", description = "Crea un nuevo post. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(request));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Actualizar post", description = "Actualiza un post existente. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    public ResponseEntity<PostResponseDTO> updatePost(
            @Parameter(description = "Id del post", example = "1") @PathVariable Long id,
            @Valid @RequestBody PostRequestDTO request) {
        return ResponseEntity.ok(postService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Eliminar post", description = "Elimina un post. Requiere rol ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post eliminado"),
            @ApiResponse(responseCode = "401", description = "Token ausente o inválido"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Post no encontrado")
    })
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "Id del post", example = "1") @PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}