// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.controller.
package com.library.authservice.controller;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import io.swagger.v3.oas.annotations.Operation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.core.Authentication;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.bind.annotation.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Map;

// Nota de lectura: Registra un controlador que responde a HTTP con datos serializados.
@RestController
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@lombok.RequiredArgsConstructor
// Nota de lectura: Declara el tipo AuthSessionController y su contrato.
public class AuthSessionController {
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final com.library.authservice.repository.UserRepository users;
    // Nota de lectura: Asocia el método HTTP y la ruta escritos con la operación del controlador.
    @GetMapping("/auth/session")
    // Nota de lectura: Documenta la operación para Swagger/OpenAPI.
    @Operation(summary = "Consultar identidad y rol vigentes de la sesión")
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public Map<String, Object> session(Authentication user) {
        // El filtro JWT obtiene este rol de la base de datos, no del token antiguo.
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        String role = user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Long accountId = users.findByEmail(user.getName()).orElseThrow().getId();
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return Map.of("id", accountId, "email", user.getName(), "role", role);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
