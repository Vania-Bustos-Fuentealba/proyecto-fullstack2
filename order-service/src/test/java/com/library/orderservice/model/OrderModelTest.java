package com.library.orderservice.model;

import com.library.orderservice.dto.OrderRequestDTO;
import com.library.orderservice.dto.OrderResponseDTO;
import com.library.orderservice.entity.OrderEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderModelTest {

    @Test
    void orderEntityShouldStoreValues() {
        OrderEntity order = new OrderEntity();
        order.setId(1L);
        order.setUserId(2L);
        order.setTotal(15990.0);
        order.setStatus("PENDING");

        assertEquals(1L, order.getId());
        assertEquals(2L, order.getUserId());
        assertEquals(15990.0, order.getTotal());
        assertEquals("PENDING", order.getStatus());
    }

    @Test
    void orderRequestDtoShouldStoreValues() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setUserId(1L);
        request.setBookId(2L);
        request.setTotal(15990.0);
        request.setStatus("PENDING");

        assertEquals(1L, request.getUserId());
        assertEquals(2L, request.getBookId());
        assertEquals(15990.0, request.getTotal());
        assertEquals("PENDING", request.getStatus());
    }

    @Test
    void orderResponseDtoShouldStoreValuesWithAllArgsConstructor() {
        OrderResponseDTO response = new OrderResponseDTO(1L, 2L, 15990.0, "PENDING");

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(15990.0, response.getTotal());
        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void orderResponseDtoShouldStoreValuesWithNoArgsConstructor() {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(1L);
        response.setUserId(2L);
        response.setTotal(15990.0);
        response.setStatus("PAID");

        assertEquals(1L, response.getId());
        assertEquals(2L, response.getUserId());
        assertEquals(15990.0, response.getTotal());
        assertEquals("PAID", response.getStatus());
    }
}