// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.nio.charset.StandardCharsets;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.http.HttpStatus;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.server.ResponseStatusException;

// Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
public final class PasswordRules {
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private PasswordRules() {}

    // BCrypt limita bytes, no caracteres: una letra acentuada puede ocupar más de uno.
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public static void validate(String password) {
        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (password != null && password.getBytes(StandardCharsets.UTF_8).length > 72)
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                    "La contraseña supera el máximo de 72 bytes UTF-8 permitido por BCrypt");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
