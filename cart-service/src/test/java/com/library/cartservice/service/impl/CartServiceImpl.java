// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.service.impl.
package com.library.cartservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.entity.Cart;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.exceptions.CartNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.repository.CartRepository;
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
// Nota de lectura: Declara el tipo CartServiceImplTest y su contrato.
class CartServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private CartRepository cartRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RestTemplate restTemplate;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private CartServiceImpl cartService;

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void addItemShouldSaveAndReturnResponseWhenBookExistsAndItemDoesNotExist() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(3);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":2,\"title\":\"Libro Test\"}");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(cartRepository.existsByUserIdAndBookId(1L, 2L))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(false);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(cartRepository.save(any(Cart.class)))
                // Nota de lectura: Encadena la operación thenAnswer al resultado anterior.
                .thenAnswer(invocation -> {
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    Cart cart = invocation.getArgument(0);
                    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                    cart.setId(10L);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return cart;
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartResponseDTO response = cartService.addItem(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3, response.getQuantity());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).getForObject("http://book-service:8080/books/2", String.class);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(cartRepository).existsByUserIdAndBookId(1L, 2L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository).save(any(Cart.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void addItemShouldThrowExceptionWhenBookIsAlreadyInCart() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(1);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":2}");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(cartRepository.existsByUserIdAndBookId(1L, 2L))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(true);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addItem(request));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("El libro ya está en el carrito de este usuario", exception.getMessage());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository, never()).save(any(Cart.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void addItemShouldThrowExceptionWhenBookServiceFails() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(999L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(1);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/999", String.class))
                // Nota de lectura: Encadena la operación thenThrow al resultado anterior.
                .thenThrow(new RuntimeException("Libro no encontrado"));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> cartService.addItem(request));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Libro no encontrado", exception.getMessage());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(cartRepository, never()).existsByUserIdAndBookId(anyLong(), anyLong());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository, never()).save(any(Cart.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getItemsByUserShouldReturnCartItems() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Cart cartOne = new Cart();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartOne.setId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartOne.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartOne.setBookId(10L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartOne.setQuantity(2);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Cart cartTwo = new Cart();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartTwo.setId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartTwo.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartTwo.setBookId(11L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartTwo.setQuantity(1);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(cartRepository.findByUserId(1L))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(List.of(cartOne, cartTwo));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<CartResponseDTO> response = cartService.getItemsByUser(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.get(0).getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.get(0).getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.get(0).getQuantity());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.get(1).getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(11L, response.get(1).getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1, response.get(1).getQuantity());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(cartRepository).findByUserId(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteCartShouldDeleteWhenCartExists() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Cart cart = new Cart();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setQuantity(1);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(cartRepository.findById(1L))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(Optional.of(cart));

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cartService.deleteCart(1L);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository).findById(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository).delete(cart);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteCartShouldThrowExceptionWhenCartDoesNotExist() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(cartRepository.findById(999L))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> cartService.deleteCart(999L));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Item de carrito no encontrado con id 999", exception.getMessage());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository).findById(999L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(cartRepository, never()).delete(any(Cart.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}