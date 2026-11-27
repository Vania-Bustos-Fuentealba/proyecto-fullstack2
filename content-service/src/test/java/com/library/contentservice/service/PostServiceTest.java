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
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.ArgumentCaptor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertEquals;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertThrows;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.AdditionalAnswers.returnsFirstArg;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.ArgumentMatchers.any;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.never;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.verify;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.when;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo PostServiceTest y su contrato.
class PostServiceTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private PostRepository postRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private PostService postService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Post post(Long id, String title) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return Post.builder()
                // Nota de lectura: Encadena la operación id al resultado anterior.
                .id(id)
                // Nota de lectura: Encadena la operación title al resultado anterior.
                .title(title)
                // Nota de lectura: Encadena la operación summary al resultado anterior.
                .summary("Resumen")
                // Nota de lectura: Encadena la operación imageUrl al resultado anterior.
                .imageUrl("https://imagenes.test/1.jpg")
                // Nota de lectura: Encadena la operación imageAlt al resultado anterior.
                .imageAlt("Texto alternativo")
                // Nota de lectura: Encadena la operación content al resultado anterior.
                .content("Contenido")
                // Nota de lectura: Encadena la operación createdAt al resultado anterior.
                .createdAt(LocalDateTime.of(2026, 11, 24, 10, 0))
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getAllPosts_devuelveLosPostsComoDto() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(postRepository.findAllByOrderByCreatedAtDesc())
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(List.of(post(1L, "Primero"), post(2L, "Segundo")));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<PostResponseDTO> resultado = postService.getAllPosts();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, resultado.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Primero", resultado.get(0).getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, resultado.get(1).getId());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getPostById_devuelveElPostCuandoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.findById(1L)).thenReturn(Optional.of(post(1L, "Primero")));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PostResponseDTO resultado = postService.getPostById(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, resultado.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Primero", resultado.getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Texto alternativo", resultado.getImageAlt());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getPostById_lanzaExcepcionCuandoNoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        ResourceNotFoundException error = assertThrows(
                // Nota de lectura: Aporta el valor ResourceNotFoundException.class a la declaración o llamada abierta.
                ResourceNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> postService.getPostById(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("No existe un post con id 99", error.getMessage());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createPost_limpiaEspaciosYGuardaLaImagenVaciaComoNull() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.save(any(Post.class))).thenAnswer(invocacion -> {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            Post guardado = invocacion.getArgument(0);
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            guardado.setId(10L);
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return guardado;
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        });

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PostRequestDTO solicitud = PostRequestDTO.builder()
                // Nota de lectura: Encadena la operación title al resultado anterior.
                .title("  Título  ")
                // Nota de lectura: Encadena la operación summary al resultado anterior.
                .summary(" Resumen ")
                // Nota de lectura: Encadena la operación imageUrl al resultado anterior.
                .imageUrl("   ")
                // Nota de lectura: Encadena la operación imageAlt al resultado anterior.
                .imageAlt(null)
                // Nota de lectura: Encadena la operación content al resultado anterior.
                .content(" Texto ")
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PostResponseDTO resultado = postService.createPost(solicitud);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(postRepository).save(captor.capture());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Post guardado = captor.getValue();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Título", guardado.getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Resumen", guardado.getSummary());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Texto", guardado.getContent());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNull(guardado.getImageUrl());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNull(guardado.getImageAlt());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, resultado.getId());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void updatePost_actualizaLosCamposDelPostExistente() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Post existente = post(1L, "Antiguo");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.findById(1L)).thenReturn(Optional.of(existente));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.save(any(Post.class))).thenAnswer(returnsFirstArg());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PostRequestDTO solicitud = PostRequestDTO.builder()
                // Nota de lectura: Encadena la operación title al resultado anterior.
                .title(" Nuevo título ")
                // Nota de lectura: Encadena la operación summary al resultado anterior.
                .summary("Nuevo resumen")
                // Nota de lectura: Encadena la operación imageUrl al resultado anterior.
                .imageUrl("https://imagenes.test/2.jpg")
                // Nota de lectura: Encadena la operación imageAlt al resultado anterior.
                .imageAlt("Nuevo alt")
                // Nota de lectura: Encadena la operación content al resultado anterior.
                .content("Nuevo contenido")
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PostResponseDTO resultado = postService.updatePost(1L, solicitud);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Nuevo título", resultado.getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Nuevo resumen", existente.getSummary());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("https://imagenes.test/2.jpg", existente.getImageUrl());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Nuevo contenido", existente.getContent());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void updatePost_lanzaExcepcionYNoGuardaCuandoNoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PostRequestDTO solicitud = PostRequestDTO.builder()
                // Nota de lectura: Encadena la operación title al resultado anterior.
                .title("Título")
                // Nota de lectura: Encadena la operación summary al resultado anterior.
                .summary("Resumen")
                // Nota de lectura: Encadena la operación content al resultado anterior.
                .content("Contenido")
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(99L, solicitud));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(postRepository, never()).save(any(Post.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deletePost_eliminaElPostExistente() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Post existente = post(1L, "Primero");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.findById(1L)).thenReturn(Optional.of(existente));

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        postService.deletePost(1L);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(postRepository).delete(existente);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deletePost_lanzaExcepcionYNoEliminaCuandoNoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(99L));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(postRepository, never()).delete(any(Post.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}