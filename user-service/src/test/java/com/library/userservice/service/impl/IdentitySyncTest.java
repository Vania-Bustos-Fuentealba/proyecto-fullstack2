// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.service.impl.
package com.library.userservice.service.impl;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.IdentityUpdateDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.entity.UserProfile;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.ArgumentMatchers.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo IdentitySyncTest y su contrato.
class IdentitySyncTest {
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock UserRepository repository;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks UserServiceImpl service;
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void preservaDatosDelPerfilAlActualizarIdentidad() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile profile = new UserProfile(42L, "Nombre", "viejo@example.com", "912345678", "Dirección 123");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findByEmail("viejo@example.com")).thenReturn(Optional.of(profile));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.save(any())).thenAnswer(call -> call.getArgument(0));
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        var result = service.updateIdentity("viejo@example.com", new IdentityUpdateDTO("Nuevo", "nuevo@example.com"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(42L, result.getId()); assertEquals("912345678", result.getPhone());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Dirección 123", result.getAddress()); assertEquals("nuevo@example.com", result.getEmail());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void borrarPerfilAusenteEsIdempotente() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        service.deleteUserByEmail("ausente@example.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(repository).deleteByEmail("ausente@example.com");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void noSobrescribeOtroPerfil() {
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(repository.existsByEmail("ocupado@example.com")).thenReturn(true);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(IllegalArgumentException.class, () -> service.updateIdentity("viejo@example.com", new IdentityUpdateDTO("Nuevo", "ocupado@example.com")));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(repository, never()).save(any());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
