// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.service.
package com.library.authservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.password.PasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.server.ResponseStatusException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.ArgumentMatchers.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo AdminAccountServiceTest y su contrato.
class AdminAccountServiceTest {
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock UserRepository repository;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock UserService registration;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock PasswordEncoder encoder;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock RestTemplate remote;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks AdminAccountService service;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    User account;
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    AdminAccountRequest request;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @BeforeEach void setup() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        account = new User(7L, "Persona", "persona@example.com", "hash", Role.CLIENT);
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        request = com.library.authservice.ProfileTestData.complete(new AdminAccountRequest());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setName("Nombre editado"); request.setEmail("nuevo@example.com"); request.setRole(Role.ADMIN);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void listaSinContraseñasConIdDeAuth() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findAll()).thenReturn(List.of(account));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(new AdminAccountResponse(7L, "Persona", "persona@example.com", Role.CLIENT), service.list().get(0));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void creaRolSolicitadoUsandoRegistroYPasswordCifrada() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setPassword("clave123");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(registration.registerUser(any())).thenReturn(new UserResponseDTO(7L, "Persona", "persona@example.com"));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.save(account)).thenReturn(account);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(Role.ADMIN, service.create(request).role());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(registration).registerUser(argThat(data -> data.getPassword().equals("clave123")));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void altaExigeContraseña() {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResponseStatusException.class, () -> service.create(request));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verifyNoInteractions(registration);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void editaYRelacionaPerfilPorCorreoNoPorId() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.save(account)).thenReturn(account);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        service.update(7L, request, "admin@example.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(remote).put(eq("http://user-service:8080/users/email/{email}"), anyMap(), eq("persona@example.com"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("nuevo@example.com", account.getEmail());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(Role.ADMIN, account.getRole());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("hash", account.getPassword());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verifyNoInteractions(encoder);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void passwordNuevaSeCifra() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.save(account)).thenReturn(account);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setPassword("nueva123");
        // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
        when(encoder.encode("nueva123")).thenReturn("nuevo-hash");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        service.update(7L, request, "admin@example.com");
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("nuevo-hash", account.getPassword());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void rechazaCorreoDuplicadoAntesDeSincronizar() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(repository.existsByEmail("nuevo@example.com")).thenReturn(true);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(409, assertThrows(ResponseStatusException.class, () -> service.update(7L, request, "admin@example.com")).getStatusCode().value());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verifyNoInteractions(remote);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void noConfirmaEdicionSiFallaElPerfil() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        doThrow(new ResourceAccessException("offline")).when(remote).put(anyString(), anyMap(), anyString());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(RestClientException.class, () -> service.update(7L, request, "admin@example.com"));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(repository, never()).save(any());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("persona@example.com", account.getEmail());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void eliminaPerfilAntesDeCuenta() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        service.delete(7L, "admin@example.com");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        InOrder order = inOrder(remote, repository);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        order.verify(remote).delete("http://user-service:8080/users/email/{email}", "persona@example.com");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        order.verify(repository).delete(account);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void conservaCuentaSiFallaEliminarPerfil() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        doThrow(new ResourceAccessException("offline")).when(remote).delete(anyString(), anyString());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(RestClientException.class, () -> service.delete(7L, "admin@example.com"));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(repository, never()).delete(any());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void protegeCuentaPropiaYPrincipal() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(repository.findById(7L)).thenReturn(Optional.of(account));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResponseStatusException.class, () -> service.delete(7L, account.getEmail()));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        request.setRole(Role.CLIENT);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResponseStatusException.class, () -> service.update(7L, request, account.getEmail()));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        account.setEmail("admin@library.cl");
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResponseStatusException.class, () -> service.delete(7L, "otro@example.com"));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verifyNoInteractions(remote);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test void inexistenteEs404() {
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(404, assertThrows(ResponseStatusException.class, () -> service.get(99L)).getStatusCode().value());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
