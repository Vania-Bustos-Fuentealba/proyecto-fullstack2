// Nota de lectura: Ubica la clase dentro del paquete com.library.authservice.dto.
package com.library.authservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Registro y administración comparten los mismos campos y validaciones del perfil.
// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo ProfileRequest y su contrato.
public class ProfileRequest {
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Pattern(regexp = "[0-9]{6,8}[0-9Kk]", message = "RUN sin puntos ni guion, entre 7 y 9 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String run;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Size(max = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String lastName;
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
    private java.time.LocalDate birthDate;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Size(max = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String region;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Size(max = 100)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String commune;
    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank @Size(max = 300)
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String address;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
