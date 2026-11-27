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
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@lombok.EqualsAndHashCode(callSuper = true)
// Nota de lectura: Declara el tipo RegisterRequestDTO y su contrato.
public class RegisterRequestDTO extends ProfileRequest {

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El nombre es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String name;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El correo es obligatorio")
    // Nota de lectura: Comprueba que el texto tenga formato de correo.
    @Email(message = "El correo debe tener un formato válido")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @jakarta.validation.constraints.Pattern(regexp = "(?i)[^\\s@]+@(duoc\\.cl|profesor\\.duoc\\.cl|gmail\\.com)", message = "Dominio de correo no permitido")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @jakarta.validation.constraints.Size(max = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "La contraseña es obligatoria")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 4, max = 10, message = "La contraseña debe tener entre 4 y 10 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String password;
    // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
    public void setName(String value) { name = value == null ? null : value.trim(); }
    // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
    public void setEmail(String value) { email = value == null ? null : value.trim().toLowerCase(java.util.Locale.ROOT); }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
