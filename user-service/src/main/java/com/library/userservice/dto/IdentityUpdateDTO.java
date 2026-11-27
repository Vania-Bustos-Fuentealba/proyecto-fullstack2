// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.dto.
package com.library.userservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.*;

// Actualiza identidad sin reemplazar el teléfono ni la dirección existentes.
// Nota de lectura: Declara el tipo IdentityUpdateDTO y su contrato.
public record IdentityUpdateDTO(
        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank @Size(min = 2, max = 100) String name,
        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank @Email @Size(max = 100) String email) {}
