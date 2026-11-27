// Nota de lectura: Ubica la clase dentro del paquete com.library.orderservice.config.
package com.library.orderservice.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.models.OpenAPI;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo SwaggerConfigTest y su contrato.
class SwaggerConfigTest {

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void customOpenAPIShouldReturnConfiguredOpenAPI() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        SwaggerConfig config = new SwaggerConfig();

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        OpenAPI openAPI = config.customOpenAPI();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(openAPI);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(openAPI.getInfo());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Order Service API", openAPI.getInfo().getTitle());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("1.0", openAPI.getInfo().getVersion());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Microservicio de pedidos - Biblioteca Online", openAPI.getInfo().getDescription());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}