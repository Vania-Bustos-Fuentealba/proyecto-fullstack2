// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.filter.
package com.library.apigateway.filter;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.Jwts;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.security.Keys;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.BeforeEach;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.params.ParameterizedTest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.params.provider.CsvSource;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.mock.web.MockHttpServletRequest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.util.ReflectionTestUtils;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.ServerRequest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.ServerResponse;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.nio.charset.StandardCharsets;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Base64;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Date;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo JwtWebMvcFilterTest y su contrato.
class JwtWebMvcFilterTest {
    // Clave exclusiva de pruebas; los casos firman JWT reales, sin simular el parser.
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private static final byte[] KEY = "clave-de-pruebas-exclusiva-de-32-bytes".getBytes(StandardCharsets.UTF_8);
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private JwtWebMvcFilter filter;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private AuthStateVerifier accounts;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @BeforeEach void setup() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        accounts = org.mockito.Mockito.mock(AuthStateVerifier.class);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        org.mockito.Mockito.lenient().when(accounts.verify(org.mockito.ArgumentMatchers.anyString())).thenAnswer(call -> {
            // Nota de lectura: Participa en la validación criptográfica del JWT con la clave del servidor.
            var claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(KEY)).parseClaimsJws(call.getArgument(0)).getBody();
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return new AuthStateVerifier.Identity(1L, claims.getSubject(), claims.get("role", String.class));
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        });
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        filter = new JwtWebMvcFilter(accounts);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ReflectionTestUtils.setField(filter, "secretKey", Base64.getEncoder().encodeToString(KEY));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private String token(String role, long remaining) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return Jwts.builder().setSubject("prueba@example.com").claim("role", role)
                // Nota de lectura: Encadena la operación setExpiration al resultado anterior.
                .setExpiration(new Date(System.currentTimeMillis() + remaining))
                // Nota de lectura: Encadena la operación signWith al resultado anterior.
                .signWith(Keys.hmacShaKeyFor(KEY)).compact();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ServerRequest request(String method, String path, String jwt) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        MockHttpServletRequest servlet = new MockHttpServletRequest(method, path);
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (jwt != null) servlet.addHeader("Authorization", "Bearer " + jwt);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ServerRequest.create(servlet, List.of());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ParameterizedTest
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @CsvSource({"GET,/users", "GET,/users/1", "POST,/users", "DELETE,/users/1",
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            "DELETE,/auth/users/1", "POST,/books", "PUT,/books/1", "PATCH,/books/1",
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            "DELETE,/books/1", "POST,/inventory", "POST,/v1/posts", "PUT,/v1/posts/1",
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            "GET,/v1/contact", "PATCH,/v1/contact/1/read", "GET,/pedidos"})
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void clienteNoPuedeAdministrar(String method, String path) throws Exception {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(403, filter.filter(request(method, path, token("CLIENT", 60000)),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> { fail("No debe alcanzar el microservicio"); return null; }).statusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(204, filter.filter(request(method, path, token("ADMIN", 60000)),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> ServerResponse.noContent().build()).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void clientePuedeConsultarLibros() throws Exception {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, filter.filter(request("GET", "/books", token("CLIENT", 60000)),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> ServerResponse.ok().build()).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void rechazaTokensAusentesInvalidosVencidosYRolesDesconocidos() throws Exception {
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (String jwt : new String[]{null, "invalido", token("ADMIN", -1000), token("DESCONOCIDO", 60000)}) {
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals(401, filter.filter(request("GET", "/users", jwt),
                    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                    req -> { fail("No debe alcanzar el microservicio"); return null; }).statusCode().value());
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void soloLoginYRegistroExactosSonPublicos() throws Exception {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, filter.filter(request("POST", "/auth/login", null),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> ServerResponse.ok().build()).statusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, filter.filter(request("POST", "/auth/register", null),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> ServerResponse.ok().build()).statusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401, filter.filter(request("POST", "/auth/login/otro", null),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> ServerResponse.ok().build()).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void noDisfrazaUnFalloDelServicioComoErrorDeSesion() {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(IllegalStateException.class, () -> filter.filter(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                request("GET", "/users", token("ADMIN", 60000)),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> { throw new IllegalStateException("Fallo del servicio"); }));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void rolRevocadoNoPuedeModificarLibrosAunqueElJwtDigaAdmin() throws Exception {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        org.mockito.Mockito.doReturn(new AuthStateVerifier.Identity(1L, "prueba@example.com", "CLIENT"))
                // Nota de lectura: Encadena la operación when al resultado anterior.
                .when(accounts).verify(org.mockito.ArgumentMatchers.anyString());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(403, filter.filter(request("PUT", "/books/7", token("ADMIN", 60000)),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> { fail("Permiso revocado"); return null; }).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void authCaidoNoAutorizaConRolAntiguo() throws Exception {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        org.mockito.Mockito.doThrow(new org.springframework.web.client.ResourceAccessException("offline"))
                // Nota de lectura: Encadena la operación when al resultado anterior.
                .when(accounts).verify(org.mockito.ArgumentMatchers.anyString());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(503, filter.filter(request("GET", "/users", token("ADMIN", 60000)),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                req -> { fail("Debe fallar cerrado"); return null; }).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @ParameterizedTest
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @CsvSource({"GET,/books", "GET,/books/1", "GET,/v1/posts", "GET,/v1/posts/1", "POST,/v1/contact"})
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void contenidoPublicoNoRequiereSesion(String method, String path) throws Exception {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, filter.filter(request(method, path, null), req -> ServerResponse.ok().build()).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void checkoutUsaIdentidadValidadaYReemplazaCabecerasDelCliente() throws Exception {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        MockHttpServletRequest servlet = new MockHttpServletRequest("POST", "/pedidos");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        servlet.addHeader("Authorization", "Bearer " + token("CLIENT", 60000));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        servlet.addHeader("X-User-Email", "otra@cuenta.cl");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        servlet.addHeader("X-User-Role", "ADMIN");
        // Nota de lectura: Conserva los elementos que cumplen la condición; sirve para búsqueda o conteo.
        filter.filter(ServerRequest.create(servlet, List.of()), req -> {
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals("prueba@example.com", req.headers().firstHeader("X-User-Email"));
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals("CLIENT", req.headers().firstHeader("X-User-Role"));
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.ok().build();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        });
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401, filter.filter(request("POST", "/pedidos", null), req -> ServerResponse.ok().build()).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void vendedorSoloConsultaProductosYOrdenes() throws Exception {
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (String path : List.of("/books", "/books/7", "/pedidos", "/pedidos/7", "/auth/session"))
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals(200, filter.filter(request("GET", path, token("SELLER", 60000)), req -> ServerResponse.ok().build()).statusCode().value());
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (String path : List.of("/users", "/auth/users", "/v1/contact"))
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals(403, filter.filter(request("GET", path, token("SELLER", 60000)), req -> { fail("No autorizado"); return null; }).statusCode().value());
        // Nota de lectura: Recorre los elementos indicados y ejecuta el bloque para cada uno.
        for (String path : List.of("/books", "/pedidos", "/auth/users", "/v1/posts", "/inventory"))
            // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
            assertEquals(403, filter.filter(request("POST", path, token("SELLER", 60000)), req -> { fail("No autorizado"); return null; }).statusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(200, filter.filter(request("POST", "/auth/refresh", token("SELLER", 60000)), req -> ServerResponse.ok().build()).statusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401, filter.filter(request("POST", "/auth/refresh", null), req -> { fail("Sin sesión"); return null; }).statusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
