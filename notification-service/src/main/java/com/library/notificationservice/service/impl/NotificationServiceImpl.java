package com.library.notificationservice.service.impl;

import com.library.notificationservice.dto.NotificationRequestDTO;
import com.library.notificationservice.dto.NotificationResponseDTO;
import com.library.notificationservice.entity.Notification;
import com.library.notificationservice.exceptions.NotificationNotFoundException;
import com.library.notificationservice.repository.NotificationRepository;
import com.library.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger =
            LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    private NotificationResponseDTO mapToResponse(Notification notification) {
        NotificationResponseDTO response = new NotificationResponseDTO();
        response.setId(notification.getId());
        response.setUserId(notification.getUserId());
        response.setMessage(notification.getMessage());
        response.setType(notification.getType());
        response.setStatus(notification.getStatus());
        return response;
    }


    @Override
    public NotificationResponseDTO createNotification(NotificationRequestDTO request) {
        logger.info("Creando notificación para userId {}", request.getUserId());

        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setStatus("PENDING");

        Notification saved = notificationRepository.save(notification);
        logger.info("Notificación creada correctamente con id {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByUser(Long userId) {
        logger.info("Listando notificaciones del userId {}", userId);
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NotificationResponseDTO getNotificationById(Long id) {
        logger.info("Buscando notificación con id {}", id);

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Notificación con id {} no encontrada", id);
                    return new NotificationNotFoundException("Notificación no encontrada con id " + id);
                });

        return mapToResponse(notification);
    }
}
