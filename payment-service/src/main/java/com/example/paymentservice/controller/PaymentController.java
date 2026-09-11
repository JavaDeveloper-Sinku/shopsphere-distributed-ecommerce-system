package com.example.paymentservice.controller;

import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.dto.response.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.createPayment(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/success")
    public ResponseEntity<PaymentResponse> markPaymentSuccess(
            @PathVariable Long id) {

        PaymentResponse response = paymentService.markPaymentSuccess(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id) {

        PaymentResponse response = paymentService.getPaymentById(id);

        return ResponseEntity.ok(response);
    }


}