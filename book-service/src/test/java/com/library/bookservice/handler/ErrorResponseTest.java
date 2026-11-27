// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.handler.
package com.library.bookservice.handler;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo ErrorResponseTest y su contrato.
class ErrorResponseTest {
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final String SECRET="clave-privada-sql-jdbc";
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final GlobalExceptionHandler handler=new GlobalExceptionHandler();
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void errorInternoNoExponeDetallesSensibles() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var general=handler.handleGeneralException(new Exception(SECRET));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(500,general.getStatusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(general.getBody().toString().contains(SECRET));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var runtime=handler.handleRuntimeException(new IllegalStateException(SECRET));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(runtime.getStatusCode().isError());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(runtime.getBody().toString().contains(SECRET));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void edicionConcurrenteDevuelveConflictoRecuperable() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var response=handler.handleConcurrentUpdate(new org.springframework.dao.OptimisticLockingFailureException(SECRET));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409,response.getStatusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(response.getBody().toString().contains(SECRET));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
