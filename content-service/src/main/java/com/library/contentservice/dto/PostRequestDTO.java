// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.dto.
package com.library.contentservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotBlank;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Size;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.AllArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Builder;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Getter;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.NoArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Setter;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Getter
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Setter
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@NoArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@AllArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@Builder
// Nota de lectura: Declara el tipo PostRequestDTO y su contrato.
public class PostRequestDTO {

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El título es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String title;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El resumen es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 300, message = "El resumen no puede superar los 300 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String summary;

    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 500, message = "La URL de la imagen no puede superar los 500 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageUrl;

    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 150, message = "El texto alternativo no puede superar los 150 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageAlt;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El contenido es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String content;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}