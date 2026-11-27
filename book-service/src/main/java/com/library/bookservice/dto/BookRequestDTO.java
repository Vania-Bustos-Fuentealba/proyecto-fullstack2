// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.dto.
package com.library.bookservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.DecimalMin;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Min;
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
// Nota de lectura: Declara el tipo BookRequestDTO y su contrato.
public class BookRequestDTO {

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El título es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 2, max = 150, message = "El título debe tener entre 2 y 150 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String title;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El autor es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 2, max = 100, message = "El autor debe tener entre 2 y 100 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String author;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El precio es obligatorio")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Double price;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El stock es obligatorio")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @Min(value = 0, message = "El stock no puede ser negativo")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer stock;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
