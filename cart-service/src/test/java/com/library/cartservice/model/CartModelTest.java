// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.model.
package com.library.cartservice.model;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.dto.CartResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.entity.Cart;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.ConstraintViolation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Validation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Validator;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.ValidatorFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Set;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo CartModelTest y su contrato.
class CartModelTest {

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void cartEntityShouldStoreValues() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Cart cart = new Cart();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setUserId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setBookId(3L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        cart.setQuantity(4);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, cart.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, cart.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3L, cart.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(4, cart.getQuantity());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void cartRequestDtoShouldStoreValues() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(3);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, request.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, request.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3, request.getQuantity());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void cartResponseDtoShouldStoreValuesWithAllArgsConstructor() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartResponseDTO response = new CartResponseDTO(1L, 2L, 3L, 4);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(4, response.getQuantity());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void cartResponseDtoShouldStoreValuesWithNoArgsConstructor() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartResponseDTO response = new CartResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setBookId(3L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setQuantity(4);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3L, response.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(4, response.getQuantity());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void cartRequestDtoShouldPassValidationWhenValid() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(1);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Validator validator = buildValidator();

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Set<ConstraintViolation<CartRequestDTO>> violations = validator.validate(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(violations.isEmpty());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void cartRequestDtoShouldFailValidationWhenInvalid() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        CartRequestDTO request = new CartRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setQuantity(0);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Validator validator = buildValidator();

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Set<ConstraintViolation<CartRequestDTO>> violations = validator.validate(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(3, violations.size());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Validator buildValidator() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return factory.getValidator();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}