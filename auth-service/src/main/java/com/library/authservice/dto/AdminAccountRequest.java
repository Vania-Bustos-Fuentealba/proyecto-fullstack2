// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.dto.
package com.library.authservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.authservice.entity.Role;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@lombok.EqualsAndHashCode(callSuper = true)
// Nota de lectura: Declara el tipo AdminAccountRequest y su contrato.
public class AdminAccountRequest extends ProfileRequest {
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Size(min = 1, max = 50)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String name;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Email @Size(max = 100)
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @jakarta.validation.constraints.Pattern(regexp = "(?:(?i)[^\\s@]+@(duoc\\.cl|profesor\\.duoc\\.cl|gmail\\.com)|admin@library\\.cl)", message = "Dominio de correo no permitido")
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @jakarta.validation.constraints.Size(max = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String email;
    // Al editar puede omitirse: no se envía ni se devuelve la contraseña existente.
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 4, max = 10)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String password;
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Role role;

    // Normaliza antes de @Valid para que espacios no oculten nombres demasiado cortos.
    // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
    public void setName(String value) { name = value == null ? null : value.trim(); }
    // Nota de lectura: Normaliza texto para comparar y guardar datos sin espacios externos o diferencias de mayúsculas.
    public void setEmail(String value) { email = value == null ? null : value.trim().toLowerCase(java.util.Locale.ROOT); }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
