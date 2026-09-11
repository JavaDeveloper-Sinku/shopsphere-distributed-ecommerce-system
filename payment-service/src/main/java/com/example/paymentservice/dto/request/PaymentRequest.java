package com.example.paymentservice.dto.request;

import com.example.paymentservice.enums.PaymentMethod;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;

    private Double amount;

    private PaymentMethod paymentMethod;
}