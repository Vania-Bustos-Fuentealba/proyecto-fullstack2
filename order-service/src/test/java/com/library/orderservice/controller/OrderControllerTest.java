package com.library.orderservice.controller;

import com.library.orderservice.dto.OrderRequestDTO;
import com.library.orderservice.dto.OrderResponseDTO;
import com.library.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    void createOrderShouldReturnCreatedResponse() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setTotal(15990.0);
        request.setStatus("PENDING");

        OrderResponseDTO serviceResponse = new OrderResponseDTO(10L, 1L, 15990.0, "PENDING");

        when(orderService.createOrder(request))
                .thenReturn(serviceResponse);

        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
        assertEquals(1L, response.getBody().getUserId());
        assertEquals(15990.0, response.getBody().getTotal());
        assertEquals("PENDING", response.getBody().getStatus());

        verify(orderService).createOrder(request);
    }

    @Test
    void createOrderShouldPropagateServiceException() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(999L);
        request.setBookId(2L);

        when(orderService.createOrder(request))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderController.createOrder(request));

        assertEquals("Usuario no encontrado", exception.getMessage());

        verify(orderService).createOrder(request);
    }
}