// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.service.impl.
package com.library.inventoryservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.dto.InventoryResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.entity.Inventory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.repository.InventoryRepository;
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
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ActiveProfiles("test")
// Nota de lectura: Declara el tipo InventoryServiceImplTest y su contrato.
class InventoryServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private InventoryRepository inventoryRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RestTemplate restTemplate;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private InventoryServiceImpl inventoryService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private InventoryRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InventoryRequestDTO dto = new InventoryRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setBookId(10L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setStock(25);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Inventory buildInventory(Long id, Long bookId, Integer stock) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Inventory inventory = new Inventory();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        inventory.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        inventory.setBookId(bookId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        inventory.setStock(stock);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return inventory;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear inventario correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreateInventorySuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InventoryRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Inventory savedInventory = buildInventory(1L, 10L, 25);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://localhost:8082/books/10", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":10,\"title\":\"Libro test\"}");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(inventoryRepository.existsByBookId(10L)).thenReturn(false);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(savedInventory);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InventoryResponseDTO response = inventoryService.createInventory(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(25, response.getStock());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, times(1))
                // Nota de lectura: Encadena la operación getForObject al resultado anterior.
                .getForObject("http://localhost:8082/books/10", String.class);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(inventoryRepository, times(1)).existsByBookId(10L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si ya existe inventario para el libro")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenInventoryAlreadyExists() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InventoryRequestDTO request = buildRequestDTO();

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://localhost:8082/books/10", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":10,\"title\":\"Libro test\"}");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(inventoryRepository.existsByBookId(10L)).thenReturn(true);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> inventoryService.createInventory(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Ya existe inventario para ese libro", exception.getMessage());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, times(1))
                // Nota de lectura: Encadena la operación getForObject al resultado anterior.
                .getForObject("http://localhost:8082/books/10", String.class);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(inventoryRepository, times(1)).existsByBookId(10L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(inventoryRepository, never()).save(any(Inventory.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe obtener inventario por bookId")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnInventoryByBookId() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Inventory inventory = buildInventory(1L, 10L, 25);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(inventoryRepository.findByBookId(10L)).thenReturn(Optional.of(inventory));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InventoryResponseDTO response = inventoryService.getInventoryByBookId(10L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(25, response.getStock());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(inventoryRepository, times(1)).findByBookId(10L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción cuando no existe inventario para el libro")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenInventoryNotFoundByBookId() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(inventoryRepository.findByBookId(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> inventoryService.getInventoryByBookId(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Inventario no encontrado para el libro con id 99", exception.getMessage());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(inventoryRepository, times(1)).findByBookId(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}