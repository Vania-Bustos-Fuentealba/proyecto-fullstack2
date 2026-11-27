// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.dto.
package com.library.authservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Email;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotBlank;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Size;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.AllArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.NoArgsConstructor;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@AllArgsConstructor
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@NoArgsConstructor
// Nota de lectura: Declara el tipo RegisterRequestDTO y su contrato.
public class RegisterRequestDTO {

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El nombre es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String name;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El correo es obligatorio")
    // Nota de lectura: Comprueba que el texto tenga formato de correo.
    @Email(message = "El correo debe tener un formato válido")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "La contraseña es obligatoria")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String password;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}