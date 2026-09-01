package com.library.paymentservice.service;


import com.library.paymentservice.dto.PaymentRequestDTO;
import com.library.paymentservice.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO request);
}
