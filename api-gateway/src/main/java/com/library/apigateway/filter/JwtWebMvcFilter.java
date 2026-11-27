// Nota de lectura: Ubica la clase dentro del paquete com.library.apigateway.filter.
package com.library.apigateway.filter;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.Claims;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.JwtException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.Jwts;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.io.Decoders;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.security.Keys;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.beans.factory.annotation.Value;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpMethod;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Component;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.HandlerFilterFunction;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.HandlerFunction;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.ServerRequest;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.servlet.function.ServerResponse;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import javax.crypto.SecretKey;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Component
// Nota de lectura: Declara el tipo JwtWebMvcFilter y su contrato.
public class JwtWebMvcFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final AuthStateVerifier accounts;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public JwtWebMvcFilter(AuthStateVerifier accounts) { this.accounts = accounts; }

    // Nota de lectura: Lee una propiedad de configuración o variable de entorno; no escribe un secreto en el código.
    @Value("${app.jwt.secret}")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String secretKey;

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String path = request.path();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        HttpMethod method = request.method();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        boolean publicRead = method == HttpMethod.GET || method == HttpMethod.HEAD;
        // Catálogo y blog son públicos; escribir libros/posts sigue siendo exclusivo de ADMIN.
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if ((publicRead && (pertenece(path, "/books") || pertenece(path, "/v1/posts")))
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                || (method == HttpMethod.POST && path.equals("/v1/contact"))
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                || method == HttpMethod.OPTIONS) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return next.handle(request);
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Solo estas rutas exactas son públicas; un prefijo parecido no autoriza acceso.
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (request.method() == HttpMethod.POST
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                && (path.equals("/auth/login") || path.equals("/auth/register"))) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return next.handle(request);
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String authHeader = request.headers().firstHeader("Authorization");

        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String token = authHeader.substring(7);

        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        Claims claims;
        // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
        try {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            // El parser comprueba la firma y el vencimiento antes de confiar en el rol.
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            claims = Jwts.parser()
                    // Nota de lectura: Participa en la validación criptográfica del JWT con la clave del servidor.
                    .setSigningKey(key)
                    // Nota de lectura: Participa en la validación criptográfica del JWT con la clave del servidor.
                    .parseClaimsJws(token)
                    // Nota de lectura: Encadena la operación getBody al resultado anterior.
                    .getBody();

        // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
        } catch (JwtException | IllegalArgumentException e) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Object role = claims.get("role");
        // Auth entrega el ID inmutable de la cuenta que debe quedar como dueña del pedido.
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        Long accountId;
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (claims.getSubject() == null || claims.getSubject().isBlank()
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                || claims.getExpiration() == null
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                || !("ADMIN".equals(role) || "CLIENT".equals(role) || "SELLER".equals(role))) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        boolean lectura = request.method() == HttpMethod.GET || request.method() == HttpMethod.HEAD;
        // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
        try {
            // Además de la firma, confirma que la cuenta exista y conserve sus permisos.
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            var identity = accounts.verify(token);
            // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
            if (identity == null || identity.id() == null || !claims.getSubject().equals(identity.email())
                    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                    || !("ADMIN".equals(identity.role()) || "CLIENT".equals(identity.role()) || "SELLER".equals(identity.role()))) {
                // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            }
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            role = identity.role();
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            accountId = identity.id();
        // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
        } catch (org.springframework.web.client.HttpClientErrorException ex) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
        } catch (org.springframework.web.client.RestClientException ex) {
            // Ante caída de Auth no concedemos permisos con información antigua.
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // El vendedor solo consulta órdenes y mantiene su propia sesión; no realiza mutaciones.
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if ("SELLER".equals(role) && !(lectura && pertenece(path, "/pedidos"))
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                && !(lectura && path.equals("/auth/session"))
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                && !(method == HttpMethod.POST && path.equals("/auth/refresh")))
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
        // Los perfiles contienen datos personales: también su lectura exige administrador.
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        boolean requiereAdmin = pertenece(path, "/users") || pertenece(path, "/auth/users")
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                || pertenece(path, "/v1/contact") || pertenece(path, "/v1/posts")
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                || (!lectura && (pertenece(path, "/books") || pertenece(path, "/inventory")));
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (lectura && pertenece(path, "/pedidos") && path.equals("/pedidos")
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                && !("ADMIN".equals(role) || "SELLER".equals(role)))
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (requiereAdmin && !"ADMIN".equals(role)) {
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Los errores del microservicio conservan su causa; no se convierten en un falso 401.
        // Sustituye cualquier identidad enviada por el navegador por la validada en Auth.
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String verifiedRole = (String) role;
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String verifiedId = accountId.toString();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ServerRequest verified = ServerRequest.from(request).headers(headers -> {
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            headers.set("X-User-Email", claims.getSubject());
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            headers.set("X-User-Id", verifiedId);
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            headers.set("X-User-Role", verifiedRole);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        }).build();
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return next.handle(verified);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private boolean pertenece(String path, String recurso) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return path.equals(recurso) || path.startsWith(recurso + "/");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
