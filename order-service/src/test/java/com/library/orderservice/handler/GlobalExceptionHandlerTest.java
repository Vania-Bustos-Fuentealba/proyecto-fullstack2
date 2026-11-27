// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.handler.
package com.library.orderservice.handler;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.orderservice.dto.OrderRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.ResponseEntity;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.validation.BeanPropertyBindingResult;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.validation.FieldError;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.MethodArgumentNotValidException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.resource.NoResourceFoundException;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Map;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Declara el tipo GlobalExceptionHandlerTest y su contrato.
class GlobalExceptionHandlerTest {

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void handleValidationExceptionShouldReturnBadRequestResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OrderRequestDTO request = new OrderRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(request, "orderRequestDTO");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        bindingResult.addError(new FieldError("orderRequestDTO", "userId", "El userId es obligatorio"));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        bindingResult.addError(new FieldError("orderRequestDTO", "bookId", "El bookId es obligatorio"));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<Map<String, Object>> response = handler.handleValidationException(exception);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(400, response.getBody().get("status"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Error de validación", response.getBody().get("message"));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Object errorsObject = response.getBody().get("errors");
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(errorsObject instanceof Map);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<?, ?> errors = (Map<?, ?>) errorsObject;
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("El userId es obligatorio", errors.get("userId"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("El bookId es obligatorio", errors.get("bookId"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void handleRuntimeExceptionShouldReturnBadRequestResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RuntimeException exception = new RuntimeException("Usuario no encontrado");

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<Map<String, Object>> response = handler.handleRuntimeException(exception);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(400, response.getBody().get("status"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Error en la solicitud", response.getBody().get("message"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Usuario no encontrado", response.getBody().get("details"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void handleGeneralExceptionShouldReturnInternalServerErrorResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Exception exception = new Exception("Error inesperado");

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<Map<String, Object>> response = handler.handleGeneralException(exception);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(500, response.getBody().get("status"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Error interno del servidor", response.getBody().get("message"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Error inesperado", response.getBody().get("details"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void handleNoResourceFoundShouldReturnNotFoundResponse() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        NoResourceFoundException exception = mock(NoResourceFoundException.class);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(exception.getMessage()).thenReturn("No static resource /orders/no-existe");

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ResponseEntity<Map<String, Object>> response = handler.handleNoResourceFound(exception);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response.getBody());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(404, response.getBody().get("status"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Recurso no encontrado", response.getBody().get("message"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("No static resource /orders/no-existe", response.getBody().get("details"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}