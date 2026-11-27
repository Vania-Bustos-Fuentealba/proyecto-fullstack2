// Prueba que un fallo de infraestructura no se confunda con validación ni revele su conexión.
package com.library.apigateway.handler;
// JUnit ejecuta el caso y comprueba estado y cuerpo de la respuesta.
import org.junit.jupiter.api.Test;
// Importa las aserciones utilizadas por este caso.
import static org.junit.jupiter.api.Assertions.*;
// Agrupa la regresión del contrato de errores del Gateway.
class ErrorResponseTest {
    // Marca el caso como prueba ejecutable.
    @Test void dependenciaCaidaEs502SinDetallesInternos() {
        // Invoca el manejador con una excepción representativa de DNS o conexión.
        var response=new GlobalExceptionHandler().handleUnavailable(new org.springframework.web.client.ResourceAccessException("host-privado:8080"));
        // La dependencia caída debe producir Bad Gateway.
        assertEquals(502,response.getStatusCode().value());
        // Los datos de infraestructura no se deben copiar al cuerpo de respuesta.
        assertFalse(response.getBody().contains("host-privado"));
    }
}
