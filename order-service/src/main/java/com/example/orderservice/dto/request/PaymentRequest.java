package com.example.orderservice.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;
    private Double amount;
    private String paymentMethod;
}
