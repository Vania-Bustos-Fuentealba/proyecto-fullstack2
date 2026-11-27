// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.repository.
package com.library.cartservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.cartservice.entity.Cart;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Declara el tipo CartRepository y su contrato.
public interface CartRepository extends JpaRepository<Cart,Long> {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Cart> findByUserId(Long userId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    Optional<Cart> findByUserIdAndBookId(Long userId, Long bookId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
