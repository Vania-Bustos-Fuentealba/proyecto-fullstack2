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
import org.junit.jupiter.api.DisplayName;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.Test;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.junit.jupiter.api.extension.ExtendWith;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.InjectMocks;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.Mock;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.mockito.junit.jupiter.MockitoExtension;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import org.springframework.test.context.ActiveProfiles;

// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.junit.jupiter.api.Assertions.*;
// Nota de lectura: Importa las dependencias indicadas para utilizarlas en este archivo.
import static org.mockito.Mockito.*;

// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ExtendWith(MockitoExtension.class)
// Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
@ActiveProfiles("test")
// Nota de lectura: Declara el tipo PaymentServiceImpTest y su contrato.
class PaymentServiceImpTest {

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @Mock
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private PaymentRepository paymentRepository;

    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @InjectMocks
    // Nota de lectura: Declara el campo y su tipo para guardar este dato en el objeto.
    private PaymentServiceImp paymentService;

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private PaymentRequestDTO buildRequestDTO() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PaymentRequestDTO dto = new PaymentRequestDTO();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setOrderId(100L);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setAmount(19990.0);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setPaymentMethod("CARD");
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        dto.setStatus("PENDING");
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return dto;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Define una función o método con los parámetros que necesita esta parte del flujo.
    private Payment buildPayment(Long id, Long orderId, Double amount, String paymentMethod, String status) {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Payment payment = new Payment();
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setId(id);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setOrderId(orderId);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setAmount(amount);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setPaymentMethod(paymentMethod);
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        payment.setStatus(status);
        // Nota de lectura: Entrega el resultado de la función o la interfaz que debe renderizar React.
        return payment;
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe crear un pago correctamente")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldCreatePaymentSuccessfully() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PaymentRequestDTO request = buildRequestDTO();
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        Payment savedPayment = buildPayment(1L, 100L, 19990.0, "CARD", "PENDING");

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(paymentRepository.existsByOrderId(100L)).thenReturn(false);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PaymentResponseDTO response = paymentService.createPayment(request);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertNotNull(response);
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(1L, response.getId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(100L, response.getOrderId());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals(19990.0, response.getAmount());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("CARD", response.getPaymentMethod());
        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("PENDING", response.getStatus());

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(paymentRepository, times(1)).existsByOrderId(100L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(paymentRepository, times(1)).save(any(Payment.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }

    // Nota de lectura: Marca el método siguiente como una prueba ejecutable por JUnit.
    @Test
    // Nota de lectura: Anotación que configura el comportamiento o la validación declarada en esta línea.
    @DisplayName("Debe lanzar excepción si ya existe un pago para la orden")
    // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
    void shouldThrowExceptionWhenPaymentAlreadyExistsForOrder() {
        // Nota de lectura: Asigna o compara el valor de esta parte de la expresión.
        PaymentRequestDTO request = buildRequestDTO();

        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        when(paymentRepository.existsByOrderId(100L)).thenReturn(true);

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        RuntimeException exception = assertThrows(
                // Nota de lectura: Aporta el valor RuntimeException.class a la declaración o llamada abierta.
                RuntimeException.class,
                // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
                () -> paymentService.createPayment(request)
        // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
        );

        // Nota de lectura: Comprueba el resultado esperado; una diferencia hace fallar esta prueba.
        assertEquals("Ya existe un pago para esa orden", exception.getMessage());
        // Nota de lectura: Invoca la operación indicada con los argumentos de este bloque.
        verify(paymentRepository, times(1)).existsByOrderId(100L);
        // Nota de lectura: Consulta o modifica los registros mediante el repositorio de persistencia.
        verify(paymentRepository, never()).save(any(Payment.class));
    // Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
    }
// Nota de lectura: Cierra o delimita la expresión, bloque o elemento abierto en las líneas anteriores.
}