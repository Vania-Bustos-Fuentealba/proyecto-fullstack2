// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.service.impl.
package com.library.reviewservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.dto.ReviewResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.entity.Review;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.exceptions.ReviewNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.repository.ReviewRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.reviewservice.service.ReviewService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo ReviewServiceImpl y su contrato.
public class ReviewServiceImpl implements ReviewService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(ReviewServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final ReviewRepository reviewRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private ReviewResponseDTO mapToResponse(Review review) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        ReviewResponseDTO response = new ReviewResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(review.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(review.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setBookId(review.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setComment(review.getComment());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setRating(review.getRating());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public ReviewResponseDTO createReview(ReviewRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando review del userId {} para bookId {}", request.getUserId(), request.getBookId());

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseUser = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://user-service:8080/users/" + request.getUserId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde user-service: {}", responseUser);

        // Nota de lectura: Comunicación HTTP interna con otro servicio; no es una operación de LocalStorage.
        String responseBook = restTemplate.getForObject(
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                "http://book-service:8080/books/" + request.getBookId(),
                // Nota de lectura: Aporta el valor String.class a la declaración o llamada abierta.
                String.class
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Respuesta desde book-service: {}", responseBook);

        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (reviewRepository.existsByUserIdAndBookId(request.getUserId(), request.getBookId())) {
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new RuntimeException("El usuario ya dejó una reseña para este libro");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Review review = new Review();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setUserId(request.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setBookId(request.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setComment(request.getComment());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        review.setRating(request.getRating());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Review saved = reviewRepository.save(review);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Review creada correctamente con id {}", saved.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(saved);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<ReviewResponseDTO> getReviewsByBook(Long bookId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando reviews del bookId {}", bookId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return reviewRepository.findByBookId(bookId)
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::mapToResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<ReviewResponseDTO> getReviewsByUser(Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando reviews del userId {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return reviewRepository.findByUserId(userId)
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .stream()
                // Nota de lectura: Transforma cada elemento, por ejemplo de entidad a DTO o de dato a fila visual.
                .map(this::mapToResponse)
                // Nota de lectura: Encadena la operación toList al resultado anterior.
                .toList();
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteReview(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Eliminando review con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Review review = reviewRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Review con id {} no encontrada", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new ReviewNotFoundException("Review no encontrada con id " + id);
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        reviewRepository.delete(review);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Review con id {} eliminada correctamente", id);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}