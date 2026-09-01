package com.library.paymentservice.service.impl;

import com.library.paymentservice.dto.PaymentRequestDTO;
import com.library.paymentservice.dto.PaymentResponseDTO;
import com.library.paymentservice.entity.Payment;
import com.library.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PaymentServiceImpTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImp paymentService;

    private PaymentRequestDTO buildRequestDTO() {
        PaymentRequestDTO dto = new PaymentRequestDTO();
        dto.setOrderId(100L);
        dto.setAmount(19990.0);
        dto.setPaymentMethod("CARD");
        dto.setStatus("PENDING");
        return dto;
    }

    private Payment buildPayment(Long id, Long orderId, Double amount, String paymentMethod, String status) {
        Payment payment = new Payment();
        payment.setId(id);
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(status);
        return payment;
    }

    @Test
    @DisplayName("Debe crear un pago correctamente")
    void shouldCreatePaymentSuccessfully() {
        PaymentRequestDTO request = buildRequestDTO();
        Payment savedPayment = buildPayment(1L, 100L, 19990.0, "CARD", "PENDING");

        when(paymentRepository.existsByOrderId(100L)).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        PaymentResponseDTO response = paymentService.createPayment(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(100L, response.getOrderId());
        assertEquals(19990.0, response.getAmount());
        assertEquals("CARD", response.getPaymentMethod());
        assertEquals("PENDING", response.getStatus());

        verify(paymentRepository, times(1)).existsByOrderId(100L);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si ya existe un pago para la orden")
    void shouldThrowExceptionWhenPaymentAlreadyExistsForOrder() {
        PaymentRequestDTO request = buildRequestDTO();

        when(paymentRepository.existsByOrderId(100L)).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> paymentService.createPayment(request)
        );

        assertEquals("Ya existe un pago para esa orden", exception.getMessage());
        verify(paymentRepository, times(1)).existsByOrderId(100L);
        verify(paymentRepository, never()).save(any(Payment.class));
    }
}