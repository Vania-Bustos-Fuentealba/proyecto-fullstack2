package com.library.authservice.service.impl;

import com.library.authservice.dto.LoginRequestDTO;
import com.library.authservice.dto.LoginResponseDTO;
import com.library.authservice.dto.RegisterRequestDTO;
import com.library.authservice.dto.UserResponseDTO;
import com.library.authservice.entity.Role;
import com.library.authservice.entity.User;
import com.library.authservice.repository.UserRepository;
import com.library.authservice.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequestDTO buildRegisterRequest() {
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setName("Juan Pérez");
        dto.setEmail("juan@email.com");
        dto.setPassword("123456");
        return dto;
    }

    private LoginRequestDTO buildLoginRequest() {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail("juan@email.com");
        dto.setPassword("123456");
        return dto;
    }

    private User buildUser(Long id, String name, String email, String password, Role role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    @Test
    @DisplayName("Debe registrar un usuario correctamente")
    void shouldRegisterUserSuccessfully() {
        RegisterRequestDTO request = buildRegisterRequest();
        User savedUser = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        when(userRepository.existsByEmail("juan@email.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(restTemplate.postForObject(
                eq("http://localhost:8089/users"),
                any(Map.class),
                eq(String.class)
        )).thenReturn("{\"id\":1}");

        UserResponseDTO response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Pérez", response.getName());
        assertEquals("juan@email.com", response.getEmail());

        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(User.class));
        verify(restTemplate, times(1))
                .postForObject(eq("http://localhost:8089/users"), any(Map.class), eq(String.class));
    }

    @Test
    @DisplayName("Debe guardar usuario con rol CLIENT al registrarse")
    void shouldAssignClientRoleWhenRegisteringUser() {
        RegisterRequestDTO request = buildRegisterRequest();
        User savedUser = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(restTemplate.postForObject(anyString(), any(Map.class), eq(String.class))).thenReturn("{\"id\":1}");

        userService.registerUser(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals("Juan Pérez", capturedUser.getName());
        assertEquals("juan@email.com", capturedUser.getEmail());
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertEquals(Role.CLIENT, capturedUser.getRole());
    }

    @Test
    @DisplayName("Debe construir correctamente el payload para user-service al registrar")
    void shouldBuildUserServicePayloadCorrectly() {
        RegisterRequestDTO request = buildRegisterRequest();
        User savedUser = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(restTemplate.postForObject(anyString(), any(Map.class), eq(String.class))).thenReturn("{\"id\":1}");

        userService.registerUser(request);

        ArgumentCaptor<Map<String, Object>> payloadCaptor = ArgumentCaptor.forClass(Map.class);

        verify(restTemplate).postForObject(
                eq("http://localhost:8089/users"),
                payloadCaptor.capture(),
                eq(String.class)
        );

        Map<String, Object> payload = payloadCaptor.getValue();

        assertEquals("Juan Pérez", payload.get("name"));
        assertEquals("juan@email.com", payload.get("email"));
        assertEquals("000000000", payload.get("phone"));
        assertEquals("Direccion temporal", payload.get("address"));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo ya está registrado")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterRequestDTO request = buildRegisterRequest();

        when(userRepository.existsByEmail("juan@email.com")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(request)
        );

        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        verify(userRepository, never()).save(any(User.class));
        verify(restTemplate, never()).postForObject(anyString(), any(), eq(String.class));
    }

    @Test
    @DisplayName("Debe hacer login correctamente y retornar token")
    void shouldLoginSuccessfully() {
        LoginRequestDTO request = buildLoginRequest();
        User user = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        when(userRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken("juan@email.com")).thenReturn("fake-jwt-token");

        LoginResponseDTO response = userService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());

        verify(userRepository, times(1)).findByEmail("juan@email.com");
        verify(passwordEncoder, times(1)).matches("123456", "encodedPassword");
        verify(jwtService, times(1)).generateToken("juan@email.com");
    }

    @Test
    @DisplayName("Debe lanzar excepción si el usuario no existe al hacer login")
    void shouldThrowExceptionWhenUserNotFoundOnLogin() {
        LoginRequestDTO request = buildLoginRequest();

        when(userRepository.findByEmail("juan@email.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login(request)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("juan@email.com");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(anyString());
    }

    @Test
    @DisplayName("Debe lanzar excepción si la contraseña es incorrecta")
    void shouldThrowExceptionWhenPasswordIsIncorrect() {
        LoginRequestDTO request = buildLoginRequest();
        User user = buildUser(1L, "Juan Pérez", "juan@email.com", "encodedPassword", Role.CLIENT);

        when(userRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.login(request)
        );

        assertEquals("Password incorrecta", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("juan@email.com");
        verify(passwordEncoder, times(1)).matches("123456", "encodedPassword");
        verify(jwtService, never()).generateToken(anyString());
    }
}