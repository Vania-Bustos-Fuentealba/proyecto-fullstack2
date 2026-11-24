package com.library.contentservice.service;

import com.library.contentservice.dto.ContactRequestDTO;
import com.library.contentservice.dto.ContactResponseDTO;
import com.library.contentservice.entity.ContactMessage;
import com.library.contentservice.exceptions.ResourceNotFoundException;
import com.library.contentservice.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactMessageRepository contactRepository;

    @Transactional
    public ContactResponseDTO createMessage(ContactRequestDTO request) {
        ContactMessage message = ContactMessage.builder()
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .message(request.getMessage().trim())
                .read(false)
                .build();
        ContactMessage saved = contactRepository.save(message);
        log.info("Mensaje de contacto recibido con id {}", saved.getId());
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ContactResponseDTO> getAllMessages() {
        return contactRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContactResponseDTO getMessageById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public ContactResponseDTO markAsRead(Long id) {
        ContactMessage message = findOrThrow(id);
        message.setRead(true);
        log.info("Mensaje {} marcado como leído", id);
        return toResponse(contactRepository.save(message));
    }

    @Transactional
    public void deleteMessage(Long id) {
        ContactMessage message = findOrThrow(id);
        contactRepository.delete(message);
        log.info("Mensaje {} eliminado", id);
    }

    private ContactMessage findOrThrow(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un mensaje con id " + id));
    }

    private ContactResponseDTO toResponse(ContactMessage message) {
        return ContactResponseDTO.builder()
                .id(message.getId())
                .name(message.getName())
                .email(message.getEmail())
                .message(message.getMessage())
                .read(message.isRead())
                .createdAt(message.getCreatedAt())
                .build();
    }
}