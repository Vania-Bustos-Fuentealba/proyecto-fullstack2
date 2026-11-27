// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.repository.
package com.library.authservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.User;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Declara el tipo UserRepository y su contrato.
public interface UserRepository extends JpaRepository<User, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    Optional<User> findByEmail(String email);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    boolean existsByEmail(String email);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}