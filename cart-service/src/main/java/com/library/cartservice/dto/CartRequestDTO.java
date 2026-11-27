// Nota de lectura: Ubica la clase dentro del paquete com.library.cartservice.dto.
package com.library.cartservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Min;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo CartRequestDTO y su contrato.
public class CartRequestDTO {
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El userId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long userId;
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El bookId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long bookId;
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "La cantidad es obligatoria")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer quantity;

// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
