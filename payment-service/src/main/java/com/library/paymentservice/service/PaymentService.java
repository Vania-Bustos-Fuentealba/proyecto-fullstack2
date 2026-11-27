// Nota de lectura: Ubica la clase dentro del paquete com.library.paymentservice.service.
package com.library.paymentservice.service;


// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.dto.PaymentRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.dto.PaymentResponseDTO;

// Nota de lectura: Declara el tipo PaymentService y su contrato.
public interface PaymentService {
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    PaymentResponseDTO createPayment(PaymentRequestDTO request);
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}
