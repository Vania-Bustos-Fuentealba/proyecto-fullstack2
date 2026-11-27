// Nota de lectura: Ubica la clase dentro del paquete com.library.paymentservice.repository.
package com.library.paymentservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.entity.Payment;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Declara el tipo PaymentRepository y su contrato.
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    Optional<Payment> findByOrderId(Long orderId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    boolean existsByOrderId(Long orderId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}