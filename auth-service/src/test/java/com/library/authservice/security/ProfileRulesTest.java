// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.security.
package com.library.authservice.security;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.ProfileTestData;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.RegisterRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;

// Nota de lectura: Declara el tipo ProfileRulesTest y su contrato.
class ProfileRulesTest {
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void validaDigitoVerificadorYFormatoDelRun() {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(ProfileRules.validRun("123456785"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(ProfileRules.validRun("123456784"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(ProfileRules.validRun("12.345.678-5"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(ProfileRules.validRun(null));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void noAceptaComunaDeOtraRegion() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var request = ProfileTestData.complete(new RegisterRequestDTO());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> ProfileRules.validate(request));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setCommune("Valparaíso");
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> ProfileRules.validate(request));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setRun("123456784");
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> ProfileRules.validate(request));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void conservaTodosLosDatosAlSincronizarYPermiteOmitirFecha() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var request = ProfileTestData.complete(new RegisterRequestDTO());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var account = new User();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ProfileRules.apply(request, account);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("123456785", account.getRun());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(request.getBirthDate(), account.getBirthDate());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(request.getAddress(), account.getAddress());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var target = new RegisterRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        ProfileRules.copy(request, target);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(request.getLastName(), target.getLastName());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setBirthDate(null);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNull(ProfileRules.payload(request, "Ana", "ana@gmail.com").get("birthDate"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
