// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.controller.
package com.library.cartservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.service.CartService;
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
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo CartControllerTest y su contrato.
class CartControllerTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private CartService cartService;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private CartController cartController;

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void healthShouldReturnServiceMessage() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String response = cartController.health();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("cart service funcionando correctamente", response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void addItemShouldReturnCreatedResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(1);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartResponseDTO serviceResponse = new CartResponseDTO(10L, 1L, 2L, 1);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(cartService.addItem(request))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(serviceResponse);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<CartResponseDTO> response = cartController.addItem(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBody().getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getBody().getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getBody().getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1, response.getBody().getQuantity());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(cartService).addItem(request);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getItemsByUserShouldReturnOkResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<CartResponseDTO> items = List.of(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                new CartResponseDTO(1L, 1L, 2L, 1),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                new CartResponseDTO(2L, 1L, 3L, 2)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(cartService.getItemsByUser(1L))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(items);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<List<CartResponseDTO>> response = cartController.getItemsByUser(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.getBody().size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getBody().get(0).getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3L, response.getBody().get(1).getBookId());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(cartService).getItemsByUser(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteCartShouldReturnNoContentResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<Void> response = cartController.deleteCart(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNull(response.getBody());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(cartService).deleteCart(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}