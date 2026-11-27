// Nota de lectura: Ubica la clase dentro del paquete com.library.notificationservice.dto.
package com.library.notificationservice.dto;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotBlank;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Size;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.Data;

// Nota de lectura: Lombok genera acceso a campos, igualdad y representación del objeto.
@Data
// Nota de lectura: Declara el tipo NotificationRequestDTO y su contrato.
public class NotificationRequestDTO {
    // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
    @NotNull(message = "El userId es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private Long userId;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El mensaje es obligatorio")
    // Nota de lectura: Limita la longitud del texto según min y max.
    @Size(min = 2, max = 255, message = "El mensaje debe tener entre 2 y 255 caracteres")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String message;

    // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
    @NotBlank(message = "El tipo de notificación es obligatorio")
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private String type;
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
