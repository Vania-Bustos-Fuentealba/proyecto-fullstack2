// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.Valid;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Min;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotBlank;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotEmpty;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.NotNull;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Pattern;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import jakarta.validation.constraints.Size;

// Nota de lectura: Declara el tipo PedidoRequest y su contrato.
public record PedidoRequest(

        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank(message = "El nombre es obligatorio")
        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String cliente,

        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank(message = "El correo es obligatorio")
        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 100, message = "El correo no puede superar los 100 caracteres")
        // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@(duoc\\.cl|profesor\\.duoc\\.cl|gmail\\.com)$",
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                flags = Pattern.Flag.CASE_INSENSITIVE,
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                message = "Solo se permiten correos @duoc.cl, @profesor.duoc.cl o @gmail.com")
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String correo,

        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank(message = "La dirección es obligatoria")
        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 300, message = "La dirección no puede superar los 300 caracteres")
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String direccion,

        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank(message = "La comuna es obligatoria")
        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 100, message = "La comuna no puede superar los 100 caracteres")
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String comuna,

        // Nota de lectura: Exige texto con contenido, no vacío ni formado solo por espacios.
        @NotBlank(message = "El método de pago es obligatorio")
        // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
        @Pattern(regexp = "webpay|transferencia",
                // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
                message = "El método de pago debe ser webpay o transferencia")
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String metodoPago,

        // Nota de lectura: Limita la longitud del texto según min y max.
        @Size(max = 20, message = "El cupón no puede superar los 20 caracteres")
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String cupon,

        // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
        @NotEmpty(message = "El pedido debe tener al menos un libro")
        // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
        @Valid
        // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
        @Size(max = 100) List<@NotNull @Valid ItemSolicitado> items) {

    // Nota de lectura: Declara el tipo ItemSolicitado y su contrato.
    public record ItemSolicitado(

            // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
            @NotNull(message = "El id del libro es obligatorio")
            // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
            @Min(1) Long libroId,

            // Nota de lectura: Rechaza un campo nulo; es una validación del servidor.
            @NotNull(message = "La cantidad es obligatoria")
            // Nota de lectura: Impone el mínimo numérico permitido por el contrato.
            @Min(value = 1, message = "La cantidad debe ser al menos 1")
            // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
            @jakarta.validation.constraints.Max(10000) Integer cantidad) {
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}