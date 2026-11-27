// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.handler.
package com.library.authservice.handler;
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
    @Test void dependenciaYConflictoConservanEstadoSinFiltrarDatos() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var dependency=handler.handleDependency(new org.springframework.web.client.RestClientException(SECRET));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(502,dependency.getStatusCode().value()); assertFalse(dependency.getBody().toString().contains(SECRET));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var conflict=handler.handleConflict(new org.springframework.dao.DataIntegrityViolationException(SECRET));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409,conflict.getStatusCode().value()); assertFalse(conflict.getBody().toString().contains(SECRET));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var unreadable=handler.handleUnreadable(new org.springframework.http.converter.HttpMessageNotReadableException(SECRET,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                new org.springframework.mock.http.MockHttpInputMessage(new byte[0])));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(400,unreadable.getStatusCode().value()); assertFalse(unreadable.getBody().toString().contains(SECRET));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void recursoInexistenteNoRevelaRutaInterna() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var missing=handler.handleNoResourceFound(new org.springframework.web.servlet.resource.NoResourceFoundException(org.springframework.http.HttpMethod.GET,"/privado","/privado"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(404,missing.getStatusCode().value()); assertFalse(missing.getBody().toString().contains("/privado"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
