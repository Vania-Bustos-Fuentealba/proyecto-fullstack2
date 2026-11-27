// Nota de lectura: Ubica la clase dentro del paquete com.library.recommendationservice.repository.
package com.library.recommendationservice.repository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.entity.Recommendation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.data.jpa.repository.JpaRepository;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo RecommendationRepository y su contrato.
public interface RecommendationRepository extends JpaRepository<Recommendation,Long> {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Recommendation> findByUserId(Long userId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<Recommendation> findByBookId(Long bookId);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
