// Prueba que un fallo de infraestructura no se confunda con validación ni revele su conexión.
// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.handler.
package com.library.apigateway.handler;
// JUnit ejecuta el caso y comprueba estado y cuerpo de la respuesta.
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Importa las aserciones utilizadas por este caso.
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Agrupa la regresión del contrato de errores del Gateway.
// Nota de lectura: Declara el tipo ErrorResponseTest y su contrato.
class ErrorResponseTest {
    // Marca el caso como prueba ejecutable.
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void dependenciaCaidaEs502SinDetallesInternos() {
        // Invoca el manejador con una excepción representativa de DNS o conexión.
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var response=new GlobalExceptionHandler().handleUnavailable(new org.springframework.web.client.ResourceAccessException("host-privado:8080"));
        // La dependencia caída debe producir Bad Gateway.
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(502,response.getStatusCode().value());
        // Los datos de infraestructura no se deben copiar al cuerpo de respuesta.
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(response.getBody().contains("host-privado"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
