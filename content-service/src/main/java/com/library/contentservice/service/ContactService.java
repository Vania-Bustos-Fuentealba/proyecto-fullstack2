// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.service.
package com.library.contentservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.dto.ContactRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.dto.ContactResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.entity.ContactMessage;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.exceptions.ResourceNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.repository.ContactMessageRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.extern.slf4j.Slf4j;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.transaction.annotation.Transactional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Slf4j
// Nota de lectura: Declara el tipo ContactService y su contrato.
public class ContactService {

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final ContactMessageRepository contactRepository;

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ContactResponseDTO createMessage(ContactRequestDTO request) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactMessage message = ContactMessage.builder()
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                .name(request.getName().trim())
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                .email(request.getEmail().trim().toLowerCase())
                // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
                .message(request.getMessage().trim())
                // Nota de lectura: Encadena la operación read al resultado anterior.
                .read(false)
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        ContactMessage saved = contactRepository.save(message);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Mensaje de contacto recibido con id {}", saved.getId());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return toResponse(saved);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional(readOnly = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<ContactResponseDTO> getAllMessages() {
        // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
        return contactRepository.findAllByOrderByCreatedAtDesc().stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::toResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional(readOnly = true)
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ContactResponseDTO getMessageById(Long id) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return toResponse(findOrThrow(id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ContactResponseDTO markAsRead(Long id) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactMessage message = findOrThrow(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        message.setRead(true);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Mensaje {} marcado como leído", id);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return toResponse(contactRepository.save(message));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Spring confirma la transacción local si termina bien y la revierte ante errores de ejecución; no abarca otra base remota.
    @Transactional
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteMessage(Long id) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactMessage message = findOrThrow(id);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        contactRepository.delete(message);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        log.info("Mensaje {} eliminado", id);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ContactMessage findOrThrow(Long id) {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        return contactRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> new ResourceNotFoundException("No existe un mensaje con id " + id));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ContactResponseDTO toResponse(ContactMessage message) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ContactResponseDTO.builder()
                // Nota de lectura: Encadena la operación id al resultado anterior.
                .id(message.getId())
                // Nota de lectura: Encadena la operación name al resultado anterior.
                .name(message.getName())
                // Nota de lectura: Encadena la operación email al resultado anterior.
                .email(message.getEmail())
                // Nota de lectura: Encadena la operación message al resultado anterior.
                .message(message.getMessage())
                // Nota de lectura: Encadena la operación read al resultado anterior.
                .read(message.isRead())
                // Nota de lectura: Encadena la operación createdAt al resultado anterior.
                .createdAt(message.getCreatedAt())
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}