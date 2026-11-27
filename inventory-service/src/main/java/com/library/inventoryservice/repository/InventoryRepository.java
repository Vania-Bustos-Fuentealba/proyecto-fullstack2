// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.repository.
package com.library.inventoryservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.inventoryservice.entity.Inventory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Declara el tipo InventoryRepository y su contrato.
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    Optional<Inventory> findByBookId(Long bookId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    boolean existsByBookId(Long bookId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}