// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.service.
package com.library.contentservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.dto.PostRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.dto.PostResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.entity.Post;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.exceptions.ResourceNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.repository.PostRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.extern.slf4j.Slf4j;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.transaction.annotation.Transactional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Slf4j
// Nota de lectura: Declara el tipo PostService y su contrato.
public class PostService {

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PostRepository postRepository;

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional(readOnly = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<PostResponseDTO> getAllPosts() {
        // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::toResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional(readOnly = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PostResponseDTO getPostById(Long id) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return toResponse(findOrThrow(id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PostResponseDTO createPost(PostRequestDTO request) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Post post = Post.builder()
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                .title(request.getTitle().trim())
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                .summary(request.getSummary().trim())
                // Nota de lectura: Encadena la operación imageUrl al resultado anterior.
                .imageUrl(clean(request.getImageUrl()))
                // Nota de lectura: Encadena la operación imageAlt al resultado anterior.
                .imageAlt(clean(request.getImageAlt()))
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                .content(request.getContent().trim())
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Post saved = postRepository.save(post);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Post creado con id {}", saved.getId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return toResponse(saved);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PostResponseDTO updatePost(Long id, PostRequestDTO request) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Post post = findOrThrow(id);
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        post.setTitle(request.getTitle().trim());
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        post.setSummary(request.getSummary().trim());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        post.setImageUrl(clean(request.getImageUrl()));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        post.setImageAlt(clean(request.getImageAlt()));
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        post.setContent(request.getContent().trim());
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Post {} actualizado", id);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return toResponse(postRepository.save(post));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deletePost(Long id) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Post post = findOrThrow(id);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        postRepository.delete(post);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Post {} eliminado", id);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Post findOrThrow(Long id) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return postRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new ResourceNotFoundException("No existe un post con id " + id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private String clean(String value) {
        // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
        return (value == null || value.isBlank()) ? null : value.trim();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private PostResponseDTO toResponse(Post post) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return PostResponseDTO.builder()
                // Nota de lectura: Encadena la operación id al resultado anterior.
                .id(post.getId())
                // Nota de lectura: Encadena la operación title al resultado anterior.
                .title(post.getTitle())
                // Nota de lectura: Encadena la operación summary al resultado anterior.
                .summary(post.getSummary())
                // Nota de lectura: Encadena la operación imageUrl al resultado anterior.
                .imageUrl(post.getImageUrl())
                // Nota de lectura: Encadena la operación imageAlt al resultado anterior.
                .imageAlt(post.getImageAlt())
                // Nota de lectura: Encadena la operación content al resultado anterior.
                .content(post.getContent())
                // Nota de lectura: Encadena la operación createdAt al resultado anterior.
                .createdAt(post.getCreatedAt())
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}