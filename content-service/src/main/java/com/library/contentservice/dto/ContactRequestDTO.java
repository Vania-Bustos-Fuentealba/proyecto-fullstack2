// Nota de lectura: Ubica la clase dentro del paquete com.library.contentservice.dto.
package com.library.contentservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Email;
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
// Nota de lectura: Declara el tipo ContactRequestDTO y su contrato.
public class ContactRequestDTO {

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El nombre es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String name;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El correo es obligatorio")
    // Nota de lectura: Comprueba que el texto tenga formato de correo.
    @Email(message = "El correo no tiene un formato válido")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El mensaje es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(max = 1000, message = "El mensaje no puede superar los 1000 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String message;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}