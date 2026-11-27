// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.service.impl.
package com.library.authservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.LoginRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.LoginResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.RegisterRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.dto.UserResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.repository.UserRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.security.JwtService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.DisplayName;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.ArgumentCaptor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.security.crypto.password.PasswordEncoder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.context.ActiveProfiles;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Map;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.ArgumentMatchers.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ActiveProfiles("test")
// Nota de lectura: Declara el tipo UserServiceImplTest y su contrato.
class UserServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.junit.jupiter.api.BeforeEach
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void configurarUrlPerfiles() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        org.springframework.test.util.ReflectionTestUtils.setField(userService, "userServiceUrl", "http://user-service:8080");
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private UserRepository userRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private JwtService jwtService;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private PasswordEncoder passwordEncoder;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private RestTemplate restTemplate;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private UserServiceImpl userService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private RegisterRequestDTO buildRegisterRequest() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RegisterRequestDTO dto = new RegisterRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setName("Juan Pérez");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setPassword("123456");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return com.library.authservice.ProfileTestData.complete(dto);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private LoginRequestDTO buildLoginRequest() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        LoginRequestDTO dto = new LoginRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setPassword("123456");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private User buildUser(Long id, String name, String email, String password, Role role) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = new User();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setName(name);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setEmail(email);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setPassword(password);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        user.setRole(role);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return user;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe registrar un usuario correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldRegisterUserSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RegisterRequestDTO request = buildRegisterRequest();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User savedUser = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(userRepository.existsByEmail("juan@email.com")).thenReturn(false);
        // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        UserResponseDTO response = userService.registerUser(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Juan Pérez", response.getName());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("juan@email.com", response.getEmail());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(passwordEncoder, times(1)).encode("123456");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).save(any(User.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate, times(1))
                // Nota de lectura: Encadena la operación put al resultado anterior.
                .put(eq("http://user-service:8080/users/email/{email}"), any(Map.class), eq("juan@email.com"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe guardar usuario con rol CLIENT al registrarse")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldAssignClientRoleWhenRegisteringUser() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RegisterRequestDTO request = buildRegisterRequest();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User savedUser = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        userService.registerUser(request);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository).save(userCaptor.capture());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User capturedUser = userCaptor.getValue();
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Juan Pérez", capturedUser.getName());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("juan@email.com", capturedUser.getEmail());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("encodedPassword", capturedUser.getPassword());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(Role.CLIENT, capturedUser.getRole());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe construir correctamente el payload para user-service al registrar")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldBuildUserServicePayloadCorrectly() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RegisterRequestDTO request = buildRegisterRequest();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User savedUser = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        // Nota de lectura: Calcula el hash BCrypt de la contraseña; no almacena la clave en texto plano.
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        userService.registerUser(request);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(restTemplate).put(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq("http://user-service:8080/users/email/{email}"),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                payloadCaptor.capture(),
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                eq("juan@email.com")
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Map<String, Object> payload = payloadCaptor.getValue();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Juan Pérez", payload.get("name"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("juan@email.com", payload.get("email"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(payload.containsKey("phone"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Calle de prueba 123", payload.get("address"));
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("123456785", payload.get("run"));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si el correo ya está registrado")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RegisterRequestDTO request = buildRegisterRequest();

        // Nota de lectura: Consulta o modifica la base de datos a través del repositorio JPA.
        when(userRepository.existsByEmail("juan@email.com")).thenReturn(true);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> userService.registerUser(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("El correo ya está registrado", ((org.springframework.web.server.ResponseStatusException) exception).getReason());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, never()).save(any(User.class));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verifyNoInteractions(restTemplate);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe hacer login correctamente y retornar token")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldLoginSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        LoginRequestDTO request = buildLoginRequest();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(jwtService.generateToken(user)).thenReturn("fake-jwt-token");

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        LoginResponseDTO response = userService.login(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("fake-jwt-token", response.getToken());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findByEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(passwordEncoder, times(1)).matches("123456", "encodedPassword");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(jwtService, times(1)).generateToken(user);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si el usuario no existe al hacer login")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenUserNotFoundOnLogin() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        LoginRequestDTO request = buildLoginRequest();

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findByEmail("juan@email.com")).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> userService.login(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401, ((org.springframework.web.server.ResponseStatusException) exception).getStatusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Correo o contraseña incorrectos", ((org.springframework.web.server.ResponseStatusException) exception).getReason());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findByEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(jwtService, never()).generateToken(any(User.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si la contraseña es incorrecta")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        LoginRequestDTO request = buildLoginRequest();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        User user = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(userRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(user));
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(false);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> userService.login(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(401, ((org.springframework.web.server.ResponseStatusException) exception).getStatusCode().value());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Correo o contraseña incorrectos", ((org.springframework.web.server.ResponseStatusException) exception).getReason());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(userRepository, times(1)).findByEmail("juan@email.com");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(passwordEncoder, times(1)).matches("123456", "encodedPassword");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(jwtService, never()).generateToken(any(User.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
