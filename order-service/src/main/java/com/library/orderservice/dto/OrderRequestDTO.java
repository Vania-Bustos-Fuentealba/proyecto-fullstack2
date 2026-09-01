package com.library.orderservice.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long userId;
    private Double total;
    private String status;
    private Long bookId;
}