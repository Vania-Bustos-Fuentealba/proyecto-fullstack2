// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.dto.
package com.library.contentservice.dto;

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

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;

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
// Nota de lectura: Declara el tipo PostResponseDTO y su contrato.
public class PostResponseDTO {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long id;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String title;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String summary;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageUrl;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String imageAlt;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String content;
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private LocalDateTime createdAt;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}