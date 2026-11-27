// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.service.impl.
package com.library.orderservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.entity.OrderEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.repository.OrderRepository;
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
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo OrderServiceImplTest y su contrato.
class OrderServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private OrderRepository orderRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RestTemplate restTemplate;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private OrderServiceImpl orderService;

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldCreateOrderSuccessfully() {
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

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":1,\"name\":\"Usuario Test\"}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":2,\"title\":\"Libro Test\"}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://inventory-service:8080/inventory/book/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":1,\"bookId\":2,\"stock\":10}");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(orderRepository.save(any(OrderEntity.class)))
                // Nota de lectura: Encadena la operación thenAnswer al resultado anterior.
                .thenAnswer(invocation -> {
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    OrderEntity order = invocation.getArgument(0);
                    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                    order.setId(100L);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return order;
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.postForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq("http://payment-service:8080/payments"),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                anyMap(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq(String.class)
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        )).thenReturn("{\"id\":1,\"status\":\"PAID\"}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.postForEntity(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq("http://notification-service:8080/notifications"),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                any(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq(String.class)
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        )).thenReturn(ResponseEntity.ok("{\"id\":1,\"status\":\"PENDING\"}"));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderResponseDTO response = orderService.createOrder(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(100L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(15990.0, response.getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", response.getStatus());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).getForObject("http://user-service:8080/users/1", String.class);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).getForObject("http://book-service:8080/books/2", String.class);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).getForObject("http://inventory-service:8080/inventory/book/2", String.class);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(orderRepository).save(any(OrderEntity.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).postForObject(eq("http://payment-service:8080/payments"), anyMap(), eq(String.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).postForEntity(eq("http://notification-service:8080/notifications"), any(), eq(String.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldUseDefaultTotalAndStatusWhenRequestDoesNotContainThem() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":1}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":2}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://inventory-service:8080/inventory/book/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"bookId\":2,\"stock\":10}");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(orderRepository.save(any(OrderEntity.class)))
                // Nota de lectura: Encadena la operación thenAnswer al resultado anterior.
                .thenAnswer(invocation -> {
                    // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                    OrderEntity order = invocation.getArgument(0);
                    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                    order.setId(101L);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return order;
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.postForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq("http://payment-service:8080/payments"),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                anyMap(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq(String.class)
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        )).thenReturn("{\"id\":1}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.postForEntity(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq("http://notification-service:8080/notifications"),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                any(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq(String.class)
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        )).thenReturn(ResponseEntity.ok("{\"id\":1}"));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderResponseDTO response = orderService.createOrder(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(101L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(0.0, response.getTotal());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", response.getStatus());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldThrowExceptionWhenUserServiceFails() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(999L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://user-service:8080/users/999", String.class))
                // Nota de lectura: Encadena la operación thenThrow al resultado anterior.
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(request));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Usuario no encontrado", exception.getMessage());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(orderRepository, never()).save(any(OrderEntity.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, never()).getForObject("http://book-service:8080/books/2", String.class);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldThrowExceptionWhenBookServiceFails() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(999L);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":1}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/999", String.class))
                // Nota de lectura: Encadena la operación thenThrow al resultado anterior.
                .thenThrow(new RuntimeException("Libro no encontrado"));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(request));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Libro no encontrado", exception.getMessage());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(orderRepository, never()).save(any(OrderEntity.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, never()).getForObject("http://inventory-service:8080/inventory/book/999", String.class);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createOrderShouldThrowExceptionWhenInventoryServiceFails() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBookId(2L);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":1}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn("{\"id\":2}");

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        when(restTemplate.getForObject("http://inventory-service:8080/inventory/book/2", String.class))
                // Nota de lectura: Encadena la operación thenThrow al resultado anterior.
                .thenThrow(new RuntimeException("Inventario no encontrado"));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(request));

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Inventario no encontrado", exception.getMessage());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(orderRepository, never()).save(any(OrderEntity.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, never()).postForObject(anyString(), any(), eq(String.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, never()).postForEntity(anyString(), any(), eq(String.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}