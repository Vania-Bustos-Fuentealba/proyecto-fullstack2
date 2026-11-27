// Nota de lectura: Ubica la clase dentro del paquete com.library.bookservice.pedido.
package com.library.bookservice.pedido;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.time.LocalDateTime;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import java.util.List;

// Nota de lectura: Declara el tipo PedidoResponse y su contrato.
public record PedidoResponse(
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        Long id,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        LocalDateTime fecha,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String cliente,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String correo,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String direccion,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String comuna,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String metodoPago,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String cupon,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        java.math.BigDecimal subtotal,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        java.math.BigDecimal descuento,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        java.math.BigDecimal envio,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        java.math.BigDecimal total,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        String estado,
        // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
        List<ItemVendido> items) {

    // Nota de lectura: Declara el tipo ItemVendido y su contrato.
    public record ItemVendido(
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            Long libroId,
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            String nombre,
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            java.math.BigDecimal precioUnitario,
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            int cantidad,
            // Nota de lectura: Completa los argumentos o la expresión iniciada en la línea anterior.
            java.math.BigDecimal subtotal) {
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}