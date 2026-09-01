package com.library.notificationservice.service.impl;

import com.library.notificationservice.dto.NotificationRequestDTO;
import com.library.notificationservice.dto.NotificationResponseDTO;
import com.library.notificationservice.entity.Notification;
import com.library.notificationservice.exceptions.NotificationNotFoundException;
import com.library.notificationservice.repository.NotificationRepository;
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
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationRequestDTO buildRequestDTO() {
        NotificationRequestDTO dto = new NotificationRequestDTO();
        dto.setUserId(1L);
        dto.setMessage("Su pedido fue procesado");
        dto.setType("EMAIL");
        return dto;
    }

    private Notification buildNotification(Long id, Long userId, String message, String type, String status) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setType(type);
        notification.setStatus(status);
        return notification;
    }

    @Test
    @DisplayName("Debe crear una notificación correctamente")
    void shouldCreateNotificationSuccessfully() {
        NotificationRequestDTO request = buildRequestDTO();
        Notification saved = buildNotification(1L, 1L, "Su pedido fue procesado", "EMAIL", "PENDING");

        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        NotificationResponseDTO response = notificationService.createNotification(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals("Su pedido fue procesado", response.getMessage());
        assertEquals("EMAIL", response.getType());
        assertEquals("PENDING", response.getStatus());

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    @DisplayName("Debe listar notificaciones por usuario")
    void shouldReturnNotificationsByUser() {
        Notification n1 = buildNotification(1L, 1L, "Mensaje 1", "EMAIL", "PENDING");
        Notification n2 = buildNotification(2L, 1L, "Mensaje 2", "SMS", "SENT");

        when(notificationRepository.findByUserId(1L)).thenReturn(List.of(n1, n2));

        List<NotificationResponseDTO> response = notificationService.getNotificationsByUser(1L);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Mensaje 1", response.get(0).getMessage());
        assertEquals("Mensaje 2", response.get(1).getMessage());

        verify(notificationRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando el usuario no tiene notificaciones")
    void shouldReturnEmptyListWhenUserHasNoNotifications() {
        when(notificationRepository.findByUserId(99L)).thenReturn(List.of());

        List<NotificationResponseDTO> response = notificationService.getNotificationsByUser(99L);

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(notificationRepository, times(1)).findByUserId(99L);
    }

    @Test
    @DisplayName("Debe obtener una notificación por id")
    void shouldReturnNotificationById() {
        Notification notification = buildNotification(1L, 1L, "Su pedido fue procesado", "EMAIL", "PENDING");

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        NotificationResponseDTO response = notificationService.getNotificationById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Su pedido fue procesado", response.getMessage());
        assertEquals("EMAIL", response.getType());

        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando la notificación no existe")
    void shouldThrowExceptionWhenNotificationNotFound() {
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        NotificationNotFoundException exception = assertThrows(
                NotificationNotFoundException.class,
                () -> notificationService.getNotificationById(99L)
        );

        assertEquals("Notificación no encontrada con id 99", exception.getMessage());
        verify(notificationRepository, times(1)).findById(99L);
    }
}