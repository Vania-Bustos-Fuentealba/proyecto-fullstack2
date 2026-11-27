// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.jsonwebtoken.JwtException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.servlet.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.servlet.http.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.core.authority.SimpleGrantedAuthority;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.core.context.SecurityContextHolder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Component;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.filter.OncePerRequestFilter;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.io.IOException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Component
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo JwtAuthenticationFilter y su contrato.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final JwtService jwtService;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final UserRepository users;

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/auth/register");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            throws ServletException, IOException {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String header = request.getHeader("Authorization");
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (header != null && header.startsWith("Bearer ")) {
            // Nota de lectura: Agrupa operaciones que pueden fallar para poder gestionar sus errores.
            try {
                // extractEmail verifica la firma y expiración; el rol se consulta en la base actual.
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                String email = jwtService.extractEmail(header.substring(7));
                // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
                users.findByEmail(email).filter(user -> jwtService.matchesAccount(header.substring(7), user)).ifPresent(user ->
                    // Nota de lectura: Establece la identidad que Spring Security utilizará para autorizar esta petición.
                    SecurityContextHolder.getContext().setAuthentication(
                        // Nota de lectura: Establece la identidad que Spring Security utilizará para autorizar esta petición.
                        new UsernamePasswordAuthenticationToken(email, null,
                            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))));
            // Nota de lectura: Captura el error y elige una respuesta controlada en lugar de mostrar éxito.
            } catch (JwtException | IllegalArgumentException ex) {
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                // Nota de lectura: Aporta el valor return a la declaración o llamada abierta.
                return;
            // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
            }
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        chain.doFilter(request, response);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
