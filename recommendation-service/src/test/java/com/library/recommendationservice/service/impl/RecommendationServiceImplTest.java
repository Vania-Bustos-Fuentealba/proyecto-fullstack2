// Nota de lectura: Ubica la clase dentro del paquete com.library.recommendationservice.service.impl.
package com.library.recommendationservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.dto.RecommendationRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.dto.RecommendationResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.entity.Recommendation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.exceptions.RecommendationNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.repository.RecommendationRepository;
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
import org.springframework.web.client.RestTemplate;

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
// Nota de lectura: Declara el tipo RecommendationServiceImplTest y su contrato.
class RecommendationServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RecommendationRepository recommendationRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RestTemplate restTemplate;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RecommendationServiceImpl recommendationService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private RecommendationRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RecommendationRequestDTO dto = new RecommendationRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setBookId(10L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setRecommendationText("Te recomiendo este libro por su narrativa");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Recommendation buildRecommendation(Long id, Long userId, Long bookId, String text) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation recommendation = new Recommendation();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setUserId(userId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setBookId(bookId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setRecommendationText(text);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return recommendation;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear una recomendación correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreateRecommendationSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RecommendationRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation saved = buildRecommendation(
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                1L, 1L, 10L, "Te recomiendo este libro por su narrativa"
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://localhost:8082/books/10", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":10,\"title\":\"Libro de prueba\"}");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(recommendationRepository.save(any(Recommendation.class))).thenReturn(saved);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RecommendationResponseDTO response = recommendationService.createRecommendation(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Te recomiendo este libro por su narrativa", response.getRecommendationText());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, times(1))
                // Nota de lectura: Encadena la operación getForObject al resultado anterior.
                .getForObject("http://localhost:8082/books/10", String.class);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, times(1)).save(any(Recommendation.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe listar recomendaciones por usuario")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnRecommendationsByUser() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation r1 = buildRecommendation(1L, 1L, 10L, "Recomendación 1");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation r2 = buildRecommendation(2L, 1L, 11L, "Recomendación 2");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(recommendationRepository.findByUserId(1L)).thenReturn(List.of(r1, r2));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<RecommendationResponseDTO> response = recommendationService.getRecommendationsByUser(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Recomendación 1", response.get(0).getRecommendationText());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Recomendación 2", response.get(1).getRecommendationText());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(recommendationRepository, times(1)).findByUserId(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe retornar lista vacía si el usuario no tiene recomendaciones")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnEmptyListWhenUserHasNoRecommendations() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(recommendationRepository.findByUserId(99L)).thenReturn(List.of());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<RecommendationResponseDTO> response = recommendationService.getRecommendationsByUser(99L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(response.isEmpty());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(recommendationRepository, times(1)).findByUserId(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe obtener una recomendación por id")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnRecommendationById() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation recommendation = buildRecommendation(1L, 1L, 10L, "Texto recomendado");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(recommendationRepository.findById(1L)).thenReturn(Optional.of(recommendation));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RecommendationResponseDTO response = recommendationService.getRecommendationById(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Texto recomendado", response.getRecommendationText());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, times(1)).findById(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción cuando la recomendación no existe")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenRecommendationNotFound() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(recommendationRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RecommendationNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor RecommendationNotFoundException.class a la declaración o llamada abierta.
                RecommendationNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> recommendationService.getRecommendationById(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Recomendación no encontrada con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, times(1)).findById(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe eliminar una recomendación correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldDeleteRecommendationSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation recommendation = buildRecommendation(1L, 1L, 10L, "Texto recomendado");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(recommendationRepository.findById(1L)).thenReturn(Optional.of(recommendation));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        doNothing().when(recommendationRepository).delete(recommendation);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> recommendationService.deleteRecommendation(1L));

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, times(1)).findById(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, times(1)).delete(recommendation);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción al eliminar una recomendación inexistente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenDeletingNonExistingRecommendation() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(recommendationRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RecommendationNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor RecommendationNotFoundException.class a la declaración o llamada abierta.
                RecommendationNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> recommendationService.deleteRecommendation(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Recomendación no encontrada con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, times(1)).findById(99L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(recommendationRepository, never()).delete(any(Recommendation.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}