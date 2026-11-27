// Nota de lectura: Ubica la clase dentro del paquete com.library.recommendationservice.dto.
package com.library.recommendationservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotBlank;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Size;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo RecommendationRequestDTO y su contrato.
public class RecommendationRequestDTO {
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El userId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long userId;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El bookId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long bookId;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El texto de recomendación es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 2, max = 500, message = "El texto debe tener entre 2 y 500 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String recommendationText;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
