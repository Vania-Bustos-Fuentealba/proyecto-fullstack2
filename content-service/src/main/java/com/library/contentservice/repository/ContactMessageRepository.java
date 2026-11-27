// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.repository.
package com.library.contentservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.contentservice.entity.ContactMessage;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Repository
// Nota de lectura: Declara el tipo ContactMessageRepository y su contrato.
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<ContactMessage> findAllByOrderByCreatedAtDesc();
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}