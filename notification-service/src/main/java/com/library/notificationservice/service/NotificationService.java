package com.library.notificationservice.service;

import com.library.notificationservice.dto.NotificationRequestDTO;
import com.library.notificationservice.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    NotificationResponseDTO createNotification(NotificationRequestDTO request);
    List<NotificationResponseDTO> getNotificationsByUser(Long userId);

    NotificationResponseDTO getNotificationById(Long id);
}

