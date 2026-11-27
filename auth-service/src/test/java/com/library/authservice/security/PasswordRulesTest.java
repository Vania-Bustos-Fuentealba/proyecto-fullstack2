// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.server.ResponseStatusException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo PasswordRulesTest y su contrato.
class PasswordRulesTest {
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void limitaBytesParaEvitarRechazoOTruncamientoDeBcrypt() {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> PasswordRules.validate("a".repeat(72)));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> PasswordRules.validate("é".repeat(36)));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> PasswordRules.validate(null));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(400, assertThrows(ResponseStatusException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> PasswordRules.validate("é".repeat(37))).getStatusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResponseStatusException.class, () -> PasswordRules.validate("a".repeat(73)));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
