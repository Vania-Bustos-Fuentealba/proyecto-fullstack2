package com.library.orderservice.service.impl;

import com.library.orderservice.dto.OrderRequestDTO;
import com.library.orderservice.dto.OrderResponseDTO;
import com.library.orderservice.entity.OrderEntity;
import com.library.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrderShouldCreateOrderSuccessfully() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setTotal(15990.0);
        request.setStatus("PENDING");

        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                .thenReturn("{\"id\":1,\"name\":\"Usuario Test\"}");

        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                .thenReturn("{\"id\":2,\"title\":\"Libro Test\"}");

        when(restTemplate.getForObject("http://inventory-service:8080/inventory/book/2", String.class))
                .thenReturn("{\"id\":1,\"bookId\":2,\"stock\":10}");

        when(orderRepository.save(any(OrderEntity.class)))
                .thenAnswer(invocation -> {
                    OrderEntity order = invocation.getArgument(0);
                    order.setId(100L);
                    return order;
                });

        when(restTemplate.postForObject(
                eq("http://payment-service:8080/payments"),
                anyMap(),
                eq(String.class)
        )).thenReturn("{\"id\":1,\"status\":\"PAID\"}");

        when(restTemplate.postForEntity(
                eq("http://notification-service:8080/notifications"),
                any(),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("{\"id\":1,\"status\":\"PENDING\"}"));

        OrderResponseDTO response = orderService.createOrder(request);

        assertEquals(100L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(15990.0, response.getTotal());
        assertEquals("PENDING", response.getStatus());

        verify(restTemplate).getForObject("http://user-service:8080/users/1", String.class);
        verify(restTemplate).getForObject("http://book-service:8080/books/2", String.class);
        verify(restTemplate).getForObject("http://inventory-service:8080/inventory/book/2", String.class);
        verify(orderRepository).save(any(OrderEntity.class));
        verify(restTemplate).postForObject(eq("http://payment-service:8080/payments"), anyMap(), eq(String.class));
        verify(restTemplate).postForEntity(eq("http://notification-service:8080/notifications"), any(), eq(String.class));
    }

    @Test
    void createOrderShouldUseDefaultTotalAndStatusWhenRequestDoesNotContainThem() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);

        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                .thenReturn("{\"id\":1}");

        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                .thenReturn("{\"id\":2}");

        when(restTemplate.getForObject("http://inventory-service:8080/inventory/book/2", String.class))
                .thenReturn("{\"bookId\":2,\"stock\":10}");

        when(orderRepository.save(any(OrderEntity.class)))
                .thenAnswer(invocation -> {
                    OrderEntity order = invocation.getArgument(0);
                    order.setId(101L);
                    return order;
                });

        when(restTemplate.postForObject(
                eq("http://payment-service:8080/payments"),
                anyMap(),
                eq(String.class)
        )).thenReturn("{\"id\":1}");

        when(restTemplate.postForEntity(
                eq("http://notification-service:8080/notifications"),
                any(),
                eq(String.class)
        )).thenReturn(ResponseEntity.ok("{\"id\":1}"));

        OrderResponseDTO response = orderService.createOrder(request);

        assertEquals(101L, response.getId());
        assertEquals(1L, response.getUserId());
        assertEquals(0.0, response.getTotal());
        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void createOrderShouldThrowExceptionWhenUserServiceFails() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(999L);
        request.setBookId(2L);

        when(restTemplate.getForObject("http://user-service:8080/users/999", String.class))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(request));

        assertEquals("Usuario no encontrado", exception.getMessage());

        verify(orderRepository, never()).save(any(OrderEntity.class));
        verify(restTemplate, never()).getForObject("http://book-service:8080/books/2", String.class);
    }

    @Test
    void createOrderShouldThrowExceptionWhenBookServiceFails() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setBookId(999L);

        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                .thenReturn("{\"id\":1}");

        when(restTemplate.getForObject("http://book-service:8080/books/999", String.class))
                .thenThrow(new RuntimeException("Libro no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(request));

        assertEquals("Libro no encontrado", exception.getMessage());

        verify(orderRepository, never()).save(any(OrderEntity.class));
        verify(restTemplate, never()).getForObject("http://inventory-service:8080/inventory/book/999", String.class);
    }

    @Test
    void createOrderShouldThrowExceptionWhenInventoryServiceFails() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);

        when(restTemplate.getForObject("http://user-service:8080/users/1", String.class))
                .thenReturn("{\"id\":1}");

        when(restTemplate.getForObject("http://book-service:8080/books/2", String.class))
                .thenReturn("{\"id\":2}");

        when(restTemplate.getForObject("http://inventory-service:8080/inventory/book/2", String.class))
                .thenThrow(new RuntimeException("Inventario no encontrado"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.createOrder(request));

        assertEquals("Inventario no encontrado", exception.getMessage());

        verify(orderRepository, never()).save(any(OrderEntity.class));
        verify(restTemplate, never()).postForObject(anyString(), any(), eq(String.class));
        verify(restTemplate, never()).postForEntity(anyString(), any(), eq(String.class));
    }
}