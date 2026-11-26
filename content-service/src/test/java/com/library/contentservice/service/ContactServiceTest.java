package com.library.contentservice.service;

import com.library.contentservice.dto.ContactRequestDTO;
import com.library.contentservice.dto.ContactResponseDTO;
import com.library.contentservice.entity.ContactMessage;
import com.library.contentservice.exceptions.ResourceNotFoundException;
import com.library.contentservice.repository.ContactMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactMessageRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    private ContactMessage mensaje(Long id) {
        return ContactMessage.builder()
                .id(id)
                .name("Ale")
                .email("ale@correo.cl")
                .message("Hola, necesito ayuda.")
                .read(false)
                .createdAt(LocalDateTime.of(2026, 11, 24, 10, 0))
                .build();
    }

    @Test
    void createMessage_normalizaLosDatosYLoGuardaComoNoLeido() {
        when(contactRepository.save(any(ContactMessage.class))).thenAnswer(invocacion -> {
            ContactMessage guardado = invocacion.getArgument(0);
            guardado.setId(5L);
            return guardado;
        });

        ContactRequestDTO solicitud = ContactRequestDTO.builder()
                .name("  Ale  ")
                .email("  ALE@Correo.CL ")
                .message(" Hola, necesito ayuda. ")
                .build();

        ContactResponseDTO resultado = contactService.createMessage(solicitud);

        ArgumentCaptor<ContactMessage> captor = ArgumentCaptor.forClass(ContactMessage.class);
        verify(contactRepository).save(captor.capture());
        ContactMessage guardado = captor.getValue();

        assertEquals("Ale", guardado.getName());
        assertEquals("ale@correo.cl", guardado.getEmail());
        assertEquals("Hola, necesito ayuda.", guardado.getMessage());
        assertFalse(guardado.isRead());
        assertEquals(5L, resultado.getId());
        assertFalse(resultado.isRead());
    }

    @Test
    void getAllMessages_devuelveLosMensajesComoDto() {
        when(contactRepository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(mensaje(1L), mensaje(2L)));

        List<ContactResponseDTO> resultado = contactService.getAllMessages();

        assertEquals(2, resultado.size());
        assertEquals(1L, resultado.get(0).getId());
        assertEquals("ale@correo.cl", resultado.get(1).getEmail());
    }

    @Test
    void getMessageById_devuelveElMensajeCuandoExiste() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(mensaje(1L)));

        ContactResponseDTO resultado = contactService.getMessageById(1L);

        assertEquals(1L, resultado.getId());
        assertEquals("Ale", resultado.getName());
    }

    @Test
    void getMessageById_lanzaExcepcionCuandoNoExiste() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException error = assertThrows(
                ResourceNotFoundException.class,
                () -> contactService.getMessageById(99L)
        );

        assertEquals("No existe un mensaje con id 99", error.getMessage());
    }

    @Test
    void markAsRead_cambiaElEstadoALeido() {
        ContactMessage existente = mensaje(1L);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(contactRepository.save(any(ContactMessage.class))).thenAnswer(returnsFirstArg());

        ContactResponseDTO resultado = contactService.markAsRead(1L);

        assertTrue(resultado.isRead());
        assertTrue(existente.isRead());
    }

    @Test
    void markAsRead_lanzaExcepcionYNoGuardaCuandoNoExiste() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.markAsRead(99L));
        verify(contactRepository, never()).save(any(ContactMessage.class));
    }

    @Test
    void deleteMessage_eliminaElMensajeExistente() {
        ContactMessage existente = mensaje(1L);
        when(contactRepository.findById(1L)).thenReturn(Optional.of(existente));

        contactService.deleteMessage(1L);

        verify(contactRepository).delete(existente);
    }

    @Test
    void deleteMessage_lanzaExcepcionYNoEliminaCuandoNoExiste() {
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> contactService.deleteMessage(99L));
        verify(contactRepository, never()).delete(any(ContactMessage.class));
    }
}