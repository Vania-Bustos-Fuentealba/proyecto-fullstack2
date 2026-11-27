// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.service.impl.
package com.library.reviewservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.entity.Review;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.exceptions.ReviewNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.repository.ReviewRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.DisplayName;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.context.ActiveProfiles;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ActiveProfiles("test")
// Nota de lectura: Declara el tipo ReviewServiceImplTest y su contrato.
class ReviewServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private ReviewRepository reviewRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private ReviewServiceImpl reviewService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ReviewRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ReviewRequestDTO dto = new ReviewRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setBookId(10L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setComment("Muy buen libro");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setRating(5);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Review buildReview(Long id, Long userId, Long bookId, String comment, Integer rating) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review review = new Review();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setUserId(userId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setBookId(bookId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setComment(comment);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setRating(rating);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return review;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear una review correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreateReviewSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ReviewRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review saved = buildReview(1L, 1L, 10L, "Muy buen libro", 5);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(reviewRepository.existsByUserIdAndBookId(1L, 10L)).thenReturn(false);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(reviewRepository.save(any(Review.class))).thenReturn(saved);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ReviewResponseDTO response = reviewService.createReview(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Muy buen libro", response.getComment());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(5, response.getRating());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(reviewRepository, times(1)).existsByUserIdAndBookId(1L, 10L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(reviewRepository, times(1)).save(any(Review.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si el usuario ya reseñó el libro")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenUserAlreadyReviewedBook() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ReviewRequestDTO request = buildRequestDTO();

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(reviewRepository.existsByUserIdAndBookId(1L, 10L)).thenReturn(true);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> reviewService.createReview(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("El usuario ya dejó una reseña para este libro", exception.getMessage());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(reviewRepository, times(1)).existsByUserIdAndBookId(1L, 10L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(reviewRepository, never()).save(any(Review.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe listar reviews por libro")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnReviewsByBook() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review r1 = buildReview(1L, 1L, 10L, "Muy bueno", 5);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review r2 = buildReview(2L, 2L, 10L, "Entretenido", 4);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(reviewRepository.findByBookId(10L)).thenReturn(List.of(r1, r2));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<ReviewResponseDTO> response = reviewService.getReviewsByBook(10L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Muy bueno", response.get(0).getComment());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Entretenido", response.get(1).getComment());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(reviewRepository, times(1)).findByBookId(10L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe retornar lista vacía si un libro no tiene reviews")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnEmptyListWhenBookHasNoReviews() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(reviewRepository.findByBookId(99L)).thenReturn(List.of());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<ReviewResponseDTO> response = reviewService.getReviewsByBook(99L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(response.isEmpty());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(reviewRepository, times(1)).findByBookId(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe listar reviews por usuario")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnReviewsByUser() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review r1 = buildReview(1L, 1L, 10L, "Muy bueno", 5);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review r2 = buildReview(2L, 1L, 11L, "Regular", 3);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(reviewRepository.findByUserId(1L)).thenReturn(List.of(r1, r2));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<ReviewResponseDTO> response = reviewService.getReviewsByUser(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.get(0).getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(11L, response.get(1).getBookId());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(reviewRepository, times(1)).findByUserId(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe eliminar una review correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldDeleteReviewSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review review = buildReview(1L, 1L, 10L, "Muy buen libro", 5);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        doNothing().when(reviewRepository).delete(review);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> reviewService.deleteReview(1L));

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(reviewRepository, times(1)).findById(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(reviewRepository, times(1)).delete(review);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción al eliminar una review inexistente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenDeletingNonExistingReview() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        ReviewNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor ReviewNotFoundException.class a la declaración o llamada abierta.
                ReviewNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> reviewService.deleteReview(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Review no encontrada con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(reviewRepository, times(1)).findById(99L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(reviewRepository, never()).delete(any(Review.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}