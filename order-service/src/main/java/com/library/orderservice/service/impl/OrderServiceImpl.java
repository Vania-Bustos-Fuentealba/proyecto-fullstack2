package com.library.orderservice.service.impl;

import com.library.orderservice.dto.OrderRequestDTO;
import com.library.orderservice.dto.OrderResponseDTO;
import com.library.orderservice.entity.OrderEntity;
import com.library.orderservice.repository.OrderRepository;
import com.library.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    private OrderResponseDTO mapToResponse(OrderEntity order) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus());
        return response;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        logger.info("Creando orden para userId {}", request.getUserId());

        String responseUser = restTemplate.getForObject(
                "http://localhost:8089/users/" + request.getUserId(),
                String.class
        );
        logger.info("Respuesta desde user-service: {}", responseUser);

        String responseBook = restTemplate.getForObject(
                "http://localhost:8082/books/" + request.getBookId(),
                String.class
        );
        logger.info("Respuesta desde book-service: {}", responseBook);

        String responseInventory = restTemplate.getForObject(
                "http://localhost:8086/inventory/book/" + request.getBookId(),
                String.class
        );
        logger.info("Respuesta desde inventory-service: {}", responseInventory);

        Double total = request.getTotal() != null ? request.getTotal() : 0.0;
        String status = request.getStatus() != null ? request.getStatus() : "PENDING";

        OrderEntity order = new OrderEntity();
        order.setUserId(request.getUserId());
        order.setTotal(total);
        order.setStatus(status);

        OrderEntity savedOrder = orderRepository.save(order);
        logger.info("Orden creada correctamente con id {}", savedOrder.getId());

        java.util.Map<String, Object> paymentRequest = new java.util.HashMap<>();
        paymentRequest.put("orderId", savedOrder.getId());
        paymentRequest.put("amount", total);
        paymentRequest.put("paymentMethod", "CARD");
        paymentRequest.put("status", "PAID");

        String responsePayment = restTemplate.postForObject(
                "http://localhost:8088/payments",
                paymentRequest,
                String.class
        );
        logger.info("Respuesta desde payment-service: {}", responsePayment);

        java.util.Map<String, Object> notificationRequest = new java.util.HashMap<>();
        notificationRequest.put("userId", request.getUserId());
        notificationRequest.put("message", "Tu orden " + savedOrder.getId() + " fue creada y pagada correctamente");
        notificationRequest.put("type", "ORDER_CREATED");

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        org.springframework.http.HttpEntity<java.util.Map<String, Object>> entity =
                new org.springframework.http.HttpEntity<>(notificationRequest, headers);

        org.springframework.http.ResponseEntity<String> responseNotification = restTemplate.postForEntity(
                "http://localhost:9090/notifications",
                entity,
                String.class
        );

        logger.info("Respuesta desde notification-service: {}", responseNotification.getBody());

        return mapToResponse(savedOrder);
    }
}