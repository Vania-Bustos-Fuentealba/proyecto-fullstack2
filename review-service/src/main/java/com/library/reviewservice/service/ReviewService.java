// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.service.
package com.library.reviewservice.service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewResponseDTO;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo ReviewService y su contrato.
public interface ReviewService {

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    ReviewResponseDTO createReview(ReviewRequestDTO request);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<ReviewResponseDTO> getReviewsByBook(Long bookId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    List<ReviewResponseDTO> getReviewsByUser(Long userId);

    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void deleteReview(Long id);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}