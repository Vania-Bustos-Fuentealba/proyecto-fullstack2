// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.filter.
package com.library.apigateway.filter;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.beans.factory.annotation.Value;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.client.SimpleClientHttpRequestFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Component;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Component
// Nota de lectura: Declara el tipo AuthStateVerifier y su contrato.
public class AuthStateVerifier {
    // Nota de lectura: Declara el tipo Identity y su contrato.
    public record Identity(Long id, String email, String role) {}
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate client;
    // Nota de lectura: Lee una propiedad de configuración o variable de entorno; no escribe un secreto en el código.
    @Value("${services.auth-url:http://auth-service:8080}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String authUrl;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public AuthStateVerifier() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var factory = new SimpleClientHttpRequestFactory();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        factory.setConnectTimeout(3000); factory.setReadTimeout(5000);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        client = new RestTemplate(factory);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public Identity verify(String token) {
        // No cacheamos roles: eliminar una cuenta o quitar ADMIN debe tener efecto inmediato.
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        HttpHeaders headers = new HttpHeaders();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        headers.setBearerAuth(token);
        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        return client.exchange(authUrl + "/auth/session", HttpMethod.GET,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                new HttpEntity<>(headers), Identity.class).getBody();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
