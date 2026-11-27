// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.repository.
package com.library.userservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.userservice.entity.UserProfile;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.Optional;

// Nota de lectura: Declara el tipo UserRepository y su contrato.
public interface UserRepository extends JpaRepository<UserProfile, Long> {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    boolean existsByEmail(String email);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteByEmail(String email);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    Optional<UserProfile> findByEmail(String email);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}