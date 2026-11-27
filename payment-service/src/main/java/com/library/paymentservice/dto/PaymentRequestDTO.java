// Nota de lectura: Ubica la clase dentro del paquete com.library.paymentservice.dto.
package com.library.paymentservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.DecimalMin;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotBlank;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo PaymentRequestDTO y su contrato.
public class PaymentRequestDTO {

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El orderId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long orderId;

    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El monto es obligatorio")
    // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Double amount;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El método de pago es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String paymentMethod;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El estado es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String status;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
