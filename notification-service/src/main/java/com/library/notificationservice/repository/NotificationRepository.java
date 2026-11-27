// Nota de lectura: Ubica la clase dentro del paquete com.library.notificationservice.repository.
package com.library.notificationservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.notificationservice.entity.Notification;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo NotificationRepository y su contrato.
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Notification> findByUserId(Long userId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
