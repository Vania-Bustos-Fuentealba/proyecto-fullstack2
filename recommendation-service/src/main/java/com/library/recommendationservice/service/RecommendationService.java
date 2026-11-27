// Nota de lectura: Ubica la clase dentro del paquete com.library.recommendationservice.service.
package com.library.recommendationservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.dto.RecommendationRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.dto.RecommendationResponseDTO;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo RecommendationService y su contrato.
public interface RecommendationService {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    RecommendationResponseDTO createRecommendation(RecommendationRequestDTO request);
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<RecommendationResponseDTO> getRecommendationsByUser(Long userId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    RecommendationResponseDTO getRecommendationById(Long id);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteRecommendation(Long id);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
