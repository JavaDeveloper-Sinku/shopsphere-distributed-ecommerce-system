package com.example.orderservice.dto.response;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Double amount;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime createdAt;
}
