// Nota de lectura: Ubica la clase dentro del paquete com.library.userservice.dto.
package com.library.userservice.dto;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.*;
// La ruta interna conserva el teléfono y actualiza solo los datos presentes.
// Nota de lectura: Declara el tipo IdentityUpdateDTO y su contrato.
public record IdentityUpdateDTO(
        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank @Size(min = 1, max = 50) String name,
        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank @Email @Size(max = 100) String email,
        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 9) String run, @Size(max = 100) String lastName,
        // Nota de lectura: Limita la longitud del texto según min y max.
        @PastOrPresent java.time.LocalDate birthDate, @Size(max = 100) String region,
        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 100) String commune, @Size(max = 300) String address) {
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public IdentityUpdateDTO(String name, String email) {
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        this(name, email, null, null, null, null, null, null);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
