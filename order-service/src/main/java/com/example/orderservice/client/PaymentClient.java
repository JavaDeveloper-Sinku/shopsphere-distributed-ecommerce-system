package com.example.orderservice.client;

import com.example.orderservice.dto.request.PaymentRequest;
import com.example.orderservice.dto.response.PaymentResponse;
import com.example.orderservice.dto.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments")
    public PaymentResponse createPayment(
            @RequestBody PaymentRequest request
    );

    @GetMapping("/api/payments/{id}")
    public PaymentResponse getPaymentById(
            @PathVariable("id") Long id
    );

    @PutMapping("/api/products/{id}/stock")
    ProductResponse updateStock(
            @PathVariable("id") Long id,
            @RequestParam("quantity") int quantity
    );
}