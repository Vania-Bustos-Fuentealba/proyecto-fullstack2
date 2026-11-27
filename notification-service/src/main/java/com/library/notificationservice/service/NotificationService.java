// Nota de lectura: Ubica la clase dentro del paquete com.library.notificationservice.service.
package com.library.notificationservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.dto.NotificationRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.dto.NotificationResponseDTO;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo NotificationService y su contrato.
public interface NotificationService {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    NotificationResponseDTO createNotification(NotificationRequestDTO request);
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<NotificationResponseDTO> getNotificationsByUser(Long userId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    NotificationResponseDTO getNotificationById(Long id);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}

