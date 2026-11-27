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
import com.library.notificationservice.service.NotificationService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo NotificationServiceImpl y su contrato.
public class NotificationServiceImpl implements NotificationService {
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(NotificationServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final NotificationRepository notificationRepository;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private NotificationResponseDTO mapToResponse(Notification notification) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        NotificationResponseDTO response = new NotificationResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(notification.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(notification.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setMessage(notification.getMessage());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setType(notification.getType());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setStatus(notification.getStatus());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }


    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public NotificationResponseDTO createNotification(NotificationRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando notificación para userId {}", request.getUserId());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Notification notification = new Notification();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setUserId(request.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setMessage(request.getMessage());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setType(request.getType());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        notification.setStatus("PENDING");

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Notification saved = notificationRepository.save(notification);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Notificación creada correctamente con id {}", saved.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(saved);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<NotificationResponseDTO> getNotificationsByUser(Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando notificaciones del userId {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return notificationRepository.findByUserId(userId)
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::mapToResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public NotificationResponseDTO getNotificationById(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Buscando notificación con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Notification notification = notificationRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Notificación con id {} no encontrada", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new NotificationNotFoundException("Notificación no encontrada con id " + id);
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(notification);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
