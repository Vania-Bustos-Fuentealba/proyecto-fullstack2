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
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
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
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Double price;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El stock es obligatorio")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @Min(value = 0, message = "El stock no puede ser negativo")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer stock;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Size(min = 3, max = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String code;
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 500)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String description;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @jakarta.validation.constraints.Pattern(regexp = "Fantasía|Clásicos|Narrativa|Fantasía romántica|Ficción histórica|Romance", message = "Selecciona una categoría válida")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String category;
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 2000)
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @jakarta.validation.constraints.Pattern(regexp = "(?:https?://[^\\s]+|/(?!/)[^\\s]*)?", message = "Usa una URL http/https o una ruta local para la imagen")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageUrl;
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @Min(0)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer criticalStock;
    // El código se normaliza antes de validar longitud y unicidad.
    // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
    public void setCode(String value) { code = value == null ? null : value.trim(); }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
