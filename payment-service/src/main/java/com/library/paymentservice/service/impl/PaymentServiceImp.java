package com.library.paymentservice.service.impl;

import com.library.paymentservice.dto.PaymentRequestDTO;
import com.library.paymentservice.dto.PaymentResponseDTO;
import com.library.paymentservice.entity.Payment;
import com.library.paymentservice.repository.PaymentRepository;
import com.library.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentServiceImp.class);

    private final PaymentRepository paymentRepository;

    private PaymentResponseDTO mapToResponse(Payment payment) {
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId(payment.getId());
        response.setOrderId(payment.getOrderId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getStatus());
        return response;
    }

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        logger.info("Creando pago para orderId {}", request.getOrderId());

        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new RuntimeException("Ya existe un pago para esa orden");
        }

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(request.getStatus());

        Payment savedPayment = paymentRepository.save(payment);

        logger.info("Pago creado correctamente con id {}", savedPayment.getId());

        return mapToResponse(savedPayment);
    }
}