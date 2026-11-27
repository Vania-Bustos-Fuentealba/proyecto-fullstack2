// Nota de lectura: Ubica la clase dentro del paquete com.library.recommendationservice.service.impl.
package com.library.recommendationservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.dto.RecommendationRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.dto.RecommendationResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.entity.Recommendation;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.exceptions.RecommendationNotFoundException;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.repository.RecommendationRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.recommendationservice.service.RecommendationService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.web.client.RestTemplate;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo RecommendationServiceImpl y su contrato.
public class RecommendationServiceImpl implements RecommendationService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(RecommendationServiceImpl.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RecommendationRepository recommendationRepository;
    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final RestTemplate restTemplate;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private RecommendationResponseDTO mapToResponse(Recommendation recommendation) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        RecommendationResponseDTO response = new RecommendationResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(recommendation.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setUserId(recommendation.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setBookId(recommendation.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setRecommendationText(recommendation.getRecommendationText());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public RecommendationResponseDTO createRecommendation(RecommendationRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando recomendación para userId {} sobre bookId {}", request.getUserId(), request.getBookId());

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

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Recommendation recommendation = new Recommendation();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setUserId(request.getUserId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setBookId(request.getBookId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        recommendation.setRecommendationText(request.getRecommendationText());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Recommendation saved = recommendationRepository.save(recommendation);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Recomendación creada correctamente con id {}", saved.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(saved);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public List<RecommendationResponseDTO> getRecommendationsByUser(Long userId) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Listando recomendaciones del userId {}", userId);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return recommendationRepository.findByUserId(userId)
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
    public RecommendationResponseDTO getRecommendationById(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Buscando recomendación con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Recommendation recommendation = recommendationRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Recomendación con id {} no encontrada", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new RecommendationNotFoundException("Recomendación no encontrada con id " + id);
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(recommendation);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public void deleteRecommendation(Long id) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Eliminando recomendación con id {}", id);

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Recommendation recommendation = recommendationRepository.findById(id)
                // Nota de lectura: Encadena la operación orElseThrow al resultado anterior.
                .orElseThrow(() -> {
                    // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
                    logger.warn("Recomendación con id {} no encontrada", id);
                    // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
                    return new RecommendationNotFoundException("Recomendación no encontrada con id " + id);
                // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
                });

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        recommendationRepository.delete(recommendation);
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Recomendación con id {} eliminada correctamente", id);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
