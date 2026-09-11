package com.example.paymentservice.service;

import com.example.paymentservice.dto.request.PaymentRequest;
import com.example.paymentservice.dto.response.PaymentResponse;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.enums.PaymentStatus;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentResponse createPayment(PaymentRequest request) {

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(savedPayment.getId())
                .orderId(savedPayment.getOrderId())
                .amount(savedPayment.getAmount())
                .paymentMethod(savedPayment.getPaymentMethod())
                .paymentStatus(savedPayment.getPaymentStatus())
                .createdAt(savedPayment.getCreatedAt())
                .build();
    }


    public PaymentResponse markPaymentSuccess(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + id)
                );

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        Payment updatedPayment = paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(updatedPayment.getId())
                .orderId(updatedPayment.getOrderId())
                .amount(updatedPayment.getAmount())
                .paymentMethod(updatedPayment.getPaymentMethod())
                .paymentStatus(updatedPayment.getPaymentStatus())
                .createdAt(updatedPayment.getCreatedAt())
                .build();
    }

    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + id)
                );

        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }


}