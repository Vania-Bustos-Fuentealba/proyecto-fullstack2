// Nota de lectura: Ubica la clase dentro del paquete com.library.inventoryservice.dto.
package com.library.inventoryservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Min;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo InventoryRequestDTO y su contrato.
public class InventoryRequestDTO {

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El bookId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long bookId;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El stock es obligatorio")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @Min(value = 0, message = "El stock no puede ser negativo")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Integer stock;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}