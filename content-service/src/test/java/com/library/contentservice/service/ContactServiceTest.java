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
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.ArgumentCaptor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertEquals;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertFalse;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertThrows;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.assertTrue;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.AdditionalAnswers.returnsFirstArg;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.ArgumentMatchers.any;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.never;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.verify;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.when;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Declara el tipo ContactServiceTest y su contrato.
class ContactServiceTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private ContactMessageRepository contactRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private ContactService contactService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ContactMessage mensaje(Long id) {
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return ContactMessage.builder()
                // Nota de lectura: Encadena la operación id al resultado anterior.
                .id(id)
                // Nota de lectura: Encadena la operación name al resultado anterior.
                .name("Ale")
                // Nota de lectura: Encadena la operación email al resultado anterior.
                .email("ale@correo.cl")
                // Nota de lectura: Encadena la operación message al resultado anterior.
                .message("Hola, necesito ayuda.")
                // Nota de lectura: Encadena la operación read al resultado anterior.
                .read(false)
                // Nota de lectura: Encadena la operación createdAt al resultado anterior.
                .createdAt(LocalDateTime.of(2026, 11, 24, 10, 0))
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void createMessage_normalizaLosDatosYLoGuardaComoNoLeido() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.save(any(ContactMessage.class))).thenAnswer(invocacion -> {
            // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
            ContactMessage guardado = invocacion.getArgument(0);
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            guardado.setId(5L);
            // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
            return guardado;
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        });

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactRequestDTO solicitud = ContactRequestDTO.builder()
                // Nota de lectura: Encadena la operación name al resultado anterior.
                .name("  Ale  ")
                // Nota de lectura: Encadena la operación email al resultado anterior.
                .email("  ALE@Correo.CL ")
                // Nota de lectura: Encadena la operación message al resultado anterior.
                .message(" Hola, necesito ayuda. ")
                // Nota de lectura: Encadena la operación build al resultado anterior.
                .build();

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactResponseDTO resultado = contactService.createMessage(solicitud);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ArgumentCaptor<ContactMessage> captor = ArgumentCaptor.forClass(ContactMessage.class);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(contactRepository).save(captor.capture());
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactMessage guardado = captor.getValue();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Ale", guardado.getName());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("ale@correo.cl", guardado.getEmail());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Hola, necesito ayuda.", guardado.getMessage());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(guardado.isRead());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(5L, resultado.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertFalse(resultado.isRead());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getAllMessages_devuelveLosMensajesComoDto() {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(contactRepository.findAllByOrderByCreatedAtDesc())
                // Nota de lectura: Encadena la operación thenReturn al resultado anterior.
                .thenReturn(List.of(mensaje(1L), mensaje(2L)));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        List<ContactResponseDTO> resultado = contactService.getAllMessages();

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(2, resultado.size());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, resultado.get(0).getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("ale@correo.cl", resultado.get(1).getEmail());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getMessageById_devuelveElMensajeCuandoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.findById(1L)).thenReturn(Optional.of(mensaje(1L)));

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactResponseDTO resultado = contactService.getMessageById(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, resultado.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Ale", resultado.getName());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void getMessageById_lanzaExcepcionCuandoNoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        ResourceNotFoundException error = assertThrows(
                // Nota de lectura: Aporta el valor ResourceNotFoundException.class a la declaración o llamada abierta.
                ResourceNotFoundException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> contactService.getMessageById(99L)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("No existe un mensaje con id 99", error.getMessage());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void markAsRead_cambiaElEstadoALeido() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactMessage existente = mensaje(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.findById(1L)).thenReturn(Optional.of(existente));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.save(any(ContactMessage.class))).thenAnswer(returnsFirstArg());

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactResponseDTO resultado = contactService.markAsRead(1L);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(resultado.isRead());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertTrue(existente.isRead());
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void markAsRead_lanzaExcepcionYNoGuardaCuandoNoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResourceNotFoundException.class, () -> contactService.markAsRead(99L));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(contactRepository, never()).save(any(ContactMessage.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteMessage_eliminaElMensajeExistente() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ContactMessage existente = mensaje(1L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.findById(1L)).thenReturn(Optional.of(existente));

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        contactService.deleteMessage(1L);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(contactRepository).delete(existente);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteMessage_lanzaExcepcionYNoEliminaCuandoNoExiste() {
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(contactRepository.findById(99L)).thenReturn(Optional.empty());

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertThrows(ResourceNotFoundException.class, () -> contactService.deleteMessage(99L));
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(contactRepository, never()).delete(any(ContactMessage.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}