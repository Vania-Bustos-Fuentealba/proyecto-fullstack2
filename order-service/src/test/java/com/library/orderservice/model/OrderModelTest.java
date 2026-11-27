// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.model.
package com.library.orderservice.model;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.entity.OrderEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo OrderModelTest y su contrato.
class OrderModelTest {

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void orderEntityShouldStoreValues() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderEntity order = new OrderEntity();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setUserId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setTotal(15990.0);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        order.setStatus("PENDING");

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, order.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, order.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(15990.0, order.getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", order.getStatus());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void orderRequestDtoShouldStoreValues() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setTotal(15990.0);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setStatus("PENDING");

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, request.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, request.getBookId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(15990.0, request.getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", request.getStatus());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void orderResponseDtoShouldStoreValuesWithAllArgsConstructor() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderResponseDTO response = new OrderResponseDTO(1L, 2L, 15990.0, "PENDING");

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(15990.0, response.getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", response.getStatus());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void orderResponseDtoShouldStoreValuesWithNoArgsConstructor() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderResponseDTO response = new OrderResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(2L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setTotal(15990.0);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setStatus("PAID");

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(15990.0, response.getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PAID", response.getStatus());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}