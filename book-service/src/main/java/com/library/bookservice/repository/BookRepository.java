// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.repository.
package com.library.bookservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.bookservice.entity.Book;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;


// Nota de lectura: Declara el tipo BookRepository y su contrato.
public interface BookRepository extends JpaRepository<Book,Long> {
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @org.springframework.data.jpa.repository.Query("select b from Book b where b.id = :id")
    // Nota de lectura: Busca y bloquea el libro hasta terminar la transacción para proteger el stock.
    Optional<Book> findForUpdate(@org.springframework.data.repository.query.Param("id") Long id);


// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
