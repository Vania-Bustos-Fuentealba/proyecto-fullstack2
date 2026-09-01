package com.library.userservice.service.impl;

import com.library.userservice.dto.UserRequestDTO;
import com.library.userservice.dto.UserResponseDTO;
import com.library.userservice.entity.UserProfile;
import com.library.userservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequestDTO buildRequestDTO() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("Juan Pérez");
        dto.setEmail("juan@email.com");
        dto.setPhone("987654321");
        dto.setAddress("Av. Siempre Viva 123");
        return dto;
    }

    private UserProfile buildUser(Long id, String name, String email, String phone, String address) {
        UserProfile user = new UserProfile();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        return user;
    }

    @Test
    @DisplayName("Debe crear un usuario correctamente")
    void shouldCreateUserSuccessfully() {
        UserRequestDTO request = buildRequestDTO();
        UserProfile savedUser = buildUser(
                1L,
                "Juan Pérez",
                "juan@email.com",
                "987654321",
                "Av. Siempre Viva 123"
        );

        when(userRepository.existsByEmail("juan@email.com")).thenReturn(false);
        when(userRepository.save(any(UserProfile.class))).thenReturn(savedUser);

        UserResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Pérez", response.getName());
        assertEquals("juan@email.com", response.getEmail());
        assertEquals("987654321", response.getPhone());
        assertEquals("Av. Siempre Viva 123", response.getAddress());

        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        verify(userRepository, times(1)).save(any(UserProfile.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el correo ya está registrado")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserRequestDTO request = buildRequestDTO();

        when(userRepository.existsByEmail("juan@email.com")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.createUser(request)
        );

        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail("juan@email.com");
        verify(userRepository, never()).save(any(UserProfile.class));
    }

    @Test
    @DisplayName("Debe listar todos los usuarios")
    void shouldReturnAllUsers() {
        UserProfile user1 = buildUser(1L, "Juan Pérez", "juan@email.com", "987654321", "Dirección 1");
        UserProfile user2 = buildUser(2L, "Ana López", "ana@email.com", "123456789", "Dirección 2");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponseDTO> response = userService.getAllUsers();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Juan Pérez", response.get(0).getName());
        assertEquals("Ana López", response.get(1).getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe obtener un usuario por id")
    void shouldReturnUserById() {
        UserProfile user = buildUser(
                1L,
                "Juan Pérez",
                "juan@email.com",
                "987654321",
                "Av. Siempre Viva 123"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.getUserById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Pérez", response.getName());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando el usuario no existe por id")
    void shouldThrowExceptionWhenUserNotFoundById() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.getUserById(99L)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debe eliminar un usuario correctamente")
    void shouldDeleteUserSuccessfully() {
        UserProfile user = buildUser(
                1L,
                "Juan Pérez",
                "juan@email.com",
                "987654321",
                "Av. Siempre Viva 123"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        assertDoesNotThrow(() -> userService.deleteUser(1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar un usuario inexistente")
    void shouldThrowExceptionWhenDeletingNonExistingUser() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.deleteUser(99L)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
        verify(userRepository, never()).delete(any(UserProfile.class));
    }
}
