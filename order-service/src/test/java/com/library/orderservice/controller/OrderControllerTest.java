// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.controller.
package com.library.orderservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.service.OrderService;
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
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo OrderControllerTest y su contrato.
class OrderControllerTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private OrderService orderService;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private OrderController orderController;

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldReturnCreatedResponse() {
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

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderResponseDTO serviceResponse = new OrderResponseDTO(10L, 1L, 15990.0, "PENDING");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(orderService.createOrder(request))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(serviceResponse);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(10L, response.getBody().getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getBody().getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(15990.0, response.getBody().getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", response.getBody().getStatus());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(orderService).createOrder(request);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldPropagateServiceException() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(999L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(orderService.createOrder(request))
                // Nota de lectura: Encadena la operación thenThrow al resultado anterior.
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderController.createOrder(request));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Usuario no encontrado", exception.getMessage());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(orderService).createOrder(request);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}