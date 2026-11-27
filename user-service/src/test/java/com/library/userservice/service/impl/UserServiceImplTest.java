// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.service.impl.
package com.library.userservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.dto.UserResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.entity.UserProfile;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.DisplayName;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.context.ActiveProfiles;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ActiveProfiles("test")
// Nota de lectura: Declara el tipo UserServiceImplTest y su contrato.
class UserServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private UserRepository userRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private UserServiceImpl userService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private UserRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserRequestDTO dto = new UserRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setName("Juan Pérez");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setPhone("987654321");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setAddress("Av. Siempre Viva 123");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private UserProfile buildUser(Long id, String name, String email, String phone, String address) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile user = new UserProfile();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setName(name);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setEmail(email);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setPhone(phone);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setAddress(address);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return user;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear un usuario correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreateUserSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile savedUser = buildUser(
                // Nota de lectura: Aporta el valor 1L a la declaración o llamada abierta.
                1L,
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Juan Pérez",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "juan@email.com",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "987654321",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Av. Siempre Viva 123"
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(userRepository.existsByEmail("juan@email.com")).thenReturn(false);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.save(any(UserProfile.class))).thenReturn(savedUser);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserResponseDTO response = userService.createUser(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Juan Pérez", response.getName());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("juan@email.com", response.getEmail());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("987654321", response.getPhone());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Av. Siempre Viva 123", response.getAddress());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).save(any(UserProfile.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si el correo ya está registrado")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserRequestDTO request = buildRequestDTO();

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(userRepository.existsByEmail("juan@email.com")).thenReturn(true);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> userService.createUser(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("El correo ya está registrado", exception.getMessage());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, never()).save(any(UserProfile.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe listar todos los usuarios")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnAllUsers() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile user1 = buildUser(1L, "Juan Pérez", "juan@email.com", "987654321", "Dirección 1");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile user2 = buildUser(2L, "Ana López", "ana@email.com", "123456789", "Dirección 2");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<UserResponseDTO> response = userService.getAllUsers();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Juan Pérez", response.get(0).getName());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Ana López", response.get(1).getName());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findAll();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe obtener un usuario por id")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnUserById() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile user = buildUser(
                // Nota de lectura: Aporta el valor 1L a la declaración o llamada abierta.
                1L,
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Juan Pérez",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "juan@email.com",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "987654321",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Av. Siempre Viva 123"
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserResponseDTO response = userService.getUserById(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Juan Pérez", response.getName());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findById(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción cuando el usuario no existe por id")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenUserNotFoundById() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> userService.getUserById(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Usuario no encontrado", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findById(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe eliminar un usuario correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldDeleteUserSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserProfile user = buildUser(
                // Nota de lectura: Aporta el valor 1L a la declaración o llamada abierta.
                1L,
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Juan Pérez",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "juan@email.com",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "987654321",
                // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
                "Av. Siempre Viva 123"
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        doNothing().when(userRepository).delete(user);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertDoesNotThrow(() -> userService.deleteUser(1L));

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findById(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).delete(user);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción al eliminar un usuario inexistente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenDeletingNonExistingUser() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> userService.deleteUser(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Usuario no encontrado", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findById(99L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, never()).delete(any(UserProfile.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
