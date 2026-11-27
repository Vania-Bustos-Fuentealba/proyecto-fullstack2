// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.config.
package com.library.apigateway.config;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.mock.web.MockHttpServletRequest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.util.ReflectionTestUtils;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo CorsConfigTest y su contrato.
class CorsConfigTest {
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void permiteElFrontendConfiguradoYRechazaOrigenAjeno() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var security = new SecurityConfig();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ReflectionTestUtils.setField(security, "allowedOrigins", List.of("http://127.0.0.1:5177"));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var cors = security.corsConfigurationSource().getCorsConfiguration(new MockHttpServletRequest("POST", "/v1/contact"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(cors);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("http://127.0.0.1:5177", cors.checkOrigin("http://127.0.0.1:5177"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNull(cors.checkOrigin("https://ajeno.example"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(cors.checkHttpMethod(org.springframework.http.HttpMethod.POST));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
