// Nota de lectura: Ubica la clase dentro del paquete com.library.notificationservice.service.impl.
package com.library.notificationservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.dto.NotificationRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.dto.NotificationResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.entity.Notification;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.exceptions.NotificationNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.repository.NotificationRepository;
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
// Nota de lectura: Declara el tipo NotificationServiceImplTest y su contrato.
class NotificationServiceImplTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private NotificationRepository notificationRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private NotificationServiceImpl notificationService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private NotificationRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        NotificationRequestDTO dto = new NotificationRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setUserId(1L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setMessage("Su pedido fue procesado");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setType("EMAIL");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Notification buildNotification(Long id, Long userId, String message, String type, String status) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Notification notification = new Notification();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setUserId(userId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setMessage(message);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setType(type);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setStatus(status);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return notification;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear una notificación correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreateNotificationSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        NotificationRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Notification saved = buildNotification(1L, 1L, "Su pedido fue procesado", "EMAIL", "PENDING");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        NotificationResponseDTO response = notificationService.createNotification(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getUserId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Su pedido fue procesado", response.getMessage());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("EMAIL", response.getType());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", response.getStatus());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(notificationRepository, times(1)).save(any(Notification.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe listar notificaciones por usuario")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnNotificationsByUser() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Notification n1 = buildNotification(1L, 1L, "Mensaje 1", "EMAIL", "PENDING");
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Notification n2 = buildNotification(2L, 1L, "Mensaje 2", "SMS", "SENT");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(notificationRepository.findByUserId(1L)).thenReturn(List.of(n1, n2));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<NotificationResponseDTO> response = notificationService.getNotificationsByUser(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, response.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Mensaje 1", response.get(0).getMessage());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Mensaje 2", response.get(1).getMessage());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(notificationRepository, times(1)).findByUserId(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe retornar lista vacía cuando el usuario no tiene notificaciones")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnEmptyListWhenUserHasNoNotifications() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(notificationRepository.findByUserId(99L)).thenReturn(List.of());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<NotificationResponseDTO> response = notificationService.getNotificationsByUser(99L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(response.isEmpty());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(notificationRepository, times(1)).findByUserId(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe obtener una notificación por id")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldReturnNotificationById() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Notification notification = buildNotification(1L, 1L, "Su pedido fue procesado", "EMAIL", "PENDING");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        NotificationResponseDTO response = notificationService.getNotificationById(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Su pedido fue procesado", response.getMessage());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("EMAIL", response.getType());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(notificationRepository, times(1)).findById(1L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción cuando la notificación no existe")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenNotificationNotFound() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(notificationRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        NotificationNotFoundException exception = assertThrows(
                // Nota de lectura: Aporta el valor NotificationNotFoundException.class a la declaración o llamada abierta.
                NotificationNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> notificationService.getNotificationById(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Notificación no encontrada con id 99", exception.getMessage());
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(notificationRepository, times(1)).findById(99L);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}