package com.library.contentservice.service;

import com.library.contentservice.dto.PostRequestDTO;
import com.library.contentservice.dto.PostResponseDTO;
import com.library.contentservice.entity.Post;
import com.library.contentservice.exceptions.ResourceNotFoundException;
import com.library.contentservice.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post(Long id, String title) {
        return Post.builder()
                .id(id)
                .title(title)
                .summary("Resumen")
                .imageUrl("https://imagenes.test/1.jpg")
                .imageAlt("Texto alternativo")
                .content("Contenido")
                .createdAt(LocalDateTime.of(2026, 11, 24, 10, 0))
                .build();
    }

    @Test
    void getAllPosts_devuelveLosPostsComoDto() {
        when(postRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(post(1L, "Primero"), post(2L, "Segundo")));

        List<PostResponseDTO> resultado = postService.getAllPosts();

        assertEquals(2, resultado.size());
        assertEquals("Primero", resultado.get(0).getTitle());
        assertEquals(2L, resultado.get(1).getId());
    }

    @Test
    void getPostById_devuelveElPostCuandoExiste() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post(1L, "Primero")));

        PostResponseDTO resultado = postService.getPostById(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Primero", resultado.getTitle());
        assertEquals("Texto alternativo", resultado.getImageAlt());
    }

    @Test
    void getPostById_lanzaExcepcionCuandoNoExiste() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException error = assertThrows(
                ResourceNotFoundException.class,
                () -> postService.getPostById(99L)
        );

        assertEquals("No existe un post con id 99", error.getMessage());
    }

    @Test
    void createPost_limpiaEspaciosYGuardaLaImagenVaciaComoNull() {
        when(postRepository.save(any(Post.class))).thenAnswer(invocacion -> {
            Post guardado = invocacion.getArgument(0);
            guardado.setId(10L);
            return guardado;
        });

        PostRequestDTO solicitud = PostRequestDTO.builder()
                .title("  Título  ")
                .summary(" Resumen ")
                .imageUrl("   ")
                .imageAlt(null)
                .content(" Texto ")
                .build();

        PostResponseDTO resultado = postService.createPost(solicitud);

        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(captor.capture());
        Post guardado = captor.getValue();

        assertEquals("Título", guardado.getTitle());
        assertEquals("Resumen", guardado.getSummary());
        assertEquals("Texto", guardado.getContent());
        assertNull(guardado.getImageUrl());
        assertNull(guardado.getImageAlt());
        assertEquals(10L, resultado.getId());
    }

    @Test
    void updatePost_actualizaLosCamposDelPostExistente() {
        Post existente = post(1L, "Antiguo");
        when(postRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(postRepository.save(any(Post.class))).thenAnswer(returnsFirstArg());

        PostRequestDTO solicitud = PostRequestDTO.builder()
                .title(" Nuevo título ")
                .summary("Nuevo resumen")
                .imageUrl("https://imagenes.test/2.jpg")
                .imageAlt("Nuevo alt")
                .content("Nuevo contenido")
                .build();

        PostResponseDTO resultado = postService.updatePost(1L, solicitud);

        assertEquals("Nuevo título", resultado.getTitle());
        assertEquals("Nuevo resumen", existente.getSummary());
        assertEquals("https://imagenes.test/2.jpg", existente.getImageUrl());
        assertEquals("Nuevo contenido", existente.getContent());
    }

    @Test
    void updatePost_lanzaExcepcionYNoGuardaCuandoNoExiste() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        PostRequestDTO solicitud = PostRequestDTO.builder()
                .title("Título")
                .summary("Resumen")
                .content("Contenido")
                .build();

        assertThrows(ResourceNotFoundException.class, () -> postService.updatePost(99L, solicitud));
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void deletePost_eliminaElPostExistente() {
        Post existente = post(1L, "Primero");
        when(postRepository.findById(1L)).thenReturn(Optional.of(existente));

        postService.deletePost(1L);

        verify(postRepository).delete(existente);
    }

    @Test
    void deletePost_lanzaExcepcionYNoEliminaCuandoNoExiste() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.deletePost(99L));
        verify(postRepository, never()).delete(any(Post.class));
    }
}