// Nota de lectura: Ubica la clase dentro del paquete com.library.paymentservice.service.impl.
package com.library.paymentservice.service.impl;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.dto.PaymentRequestDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.dto.PaymentResponseDTO;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.entity.Payment;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.repository.PaymentRepository;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import com.library.paymentservice.service.PaymentService;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import lombok.RequiredArgsConstructor;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.Logger;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.slf4j.LoggerFactory;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.stereotype.Service;

// Nota de lectura: Permite que Spring cree e inyecte esta clase.
@Service
// Nota de lectura: Lombok genera el constructor de las dependencias final.
@RequiredArgsConstructor
// Nota de lectura: Declara el tipo PaymentServiceImp y su contrato.
public class PaymentServiceImp implements PaymentService {

    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private static final Logger logger =
            // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
            LoggerFactory.getLogger(PaymentServiceImp.class);

    // Nota de lectura: Declara una dependencia que se recibe al construir esta clase.
    private final PaymentRepository paymentRepository;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private PaymentResponseDTO mapToResponse(Payment payment) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PaymentResponseDTO response = new PaymentResponseDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setId(payment.getId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setOrderId(payment.getOrderId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setAmount(payment.getAmount());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setPaymentMethod(payment.getPaymentMethod());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        response.setStatus(payment.getStatus());
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return response;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Indica que el método implementa o redefine un contrato heredado.
    @Override
    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Creando pago para orderId {}", request.getOrderId());

        // Nota de lectura: Evalúa una condición. El bloque solo continúa por esta rama si se cumple.
        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            // Nota de lectura: Interrumpe la operación y comunica el error al manejador correspondiente.
            throw new RuntimeException("Ya existe un pago para esa orden");
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        }

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Payment payment = new Payment();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setOrderId(request.getOrderId());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setAmount(request.getAmount());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setPaymentMethod(request.getPaymentMethod());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setStatus(request.getStatus());

        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        Payment savedPayment = paymentRepository.save(payment);

        // Nota de lectura: Registra información de diagnóstico para seguir la operación en el servidor.
        logger.info("Pago creado correctamente con id {}", savedPayment.getId());

        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return mapToResponse(savedPayment);
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}