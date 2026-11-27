// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.repository.
package com.library.reviewservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.entity.Review;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo ReviewRepository y su contrato.
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Review> findByBookId(Long bookId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Review> findByUserId(Long userId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}