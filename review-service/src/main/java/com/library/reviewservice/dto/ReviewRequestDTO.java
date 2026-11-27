// Nota de lectura: Ubica la clase dentro del paquete com.library.reviewservice.dto.
package com.library.reviewservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo ReviewRequestDTO y su contrato.
public class ReviewRequestDTO {
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El userId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long userId;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El bookId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long bookId;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El comentario es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 2, max = 500, message = "El comentario debe tener entre 2 y 500 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String comment;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El rating es obligatorio")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @Min(value = 1, message = "El rating mínimo es 1")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Max(value = 5, message = "El rating máximo es 5")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer rating;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
