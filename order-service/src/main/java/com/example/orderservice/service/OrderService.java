package com.example.orderservice.service;

import com.example.orderservice.client.PaymentClient;
import com.example.orderservice.client.ProductClient;

import com.example.orderservice.dto.request.PaymentRequest;
import com.example.orderservice.dto.response.PaymentResponse;
import com.example.orderservice.dto.response.ProductResponse;

import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.exception.OutOfStockException;
import com.example.orderservice.exception.ProductNotFoundException;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;

import feign.FeignException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductClient productClient;
    private final PaymentClient paymentClient;

    // Constructor
    public OrderService(
            OrderRepository orderRepository,
            ProductClient productClient,
            PaymentClient paymentClient
    ) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
    }

    public ProductResponse getProductByIdUsingFeign(Long productId) {
        return productClient.getProductById(productId);
    }

    // Just testing service
    public void testFeignCall(Long productId) {

        ProductResponse product =
                productClient.getProductById(productId);

        System.out.println(
                "Product fetched via Feign: " + product.getName()
        );
    }

    public Order placeOrder(Long productId, int quantity) {

        ProductResponse productResponse;

        try {
            productResponse =
                    productClient.getProductById(productId);

        } catch (FeignException.NotFound ex) {

            throw new ProductNotFoundException(
                    "Product not found with id: " + productId
            );
        }

        if (productResponse == null ||
                productResponse.getStock() == null) {

            throw new ProductNotFoundException(
                    "Product not found with id: " + productId
            );
        }

        // Check stock
        if (productResponse.getStock() < quantity) {

            throw new OutOfStockException(
                    "Product is out of stock"
            );
        }

        // Decrease stock
        productClient.updateStock(
                productId,
                quantity
        );

        Order order = new Order();

        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus("Confirmed");

        Order saveOrder =
                orderRepository.save(order);

        System.out.println(
                "Order placed successfully for "
                        + productResponse.getName()
        );

        return saveOrder;
    }

    public Order createOrder(Order order) {

        // 1. Get product
        ProductResponse productResponse =
                productClient.getProductById(
                        order.getProductId()
                );

        // 2. Check product
        if (productResponse == null) {

            throw new ProductNotFoundException(
                    "Product not found with id: "
                            + order.getProductId()
            );
        }

        // 3. Check stock
        if (productResponse.getStock() == null ||
                productResponse.getStock() < order.getQuantity()) {

            throw new OutOfStockException(
                    "Insufficient stock for product: "
                            + order.getProductId()
            );
        }

        // 4. Calculate total amount
        Double totalAmount =
                productResponse.getPrice()
                        * order.getQuantity();

        // 5. Decrease product stock
        productClient.updateStock(
                order.getProductId(),
                order.getQuantity()
        );

        // 6. Set order status
        order.setStatus("PENDING");

        // 7. Save order
        Order savedOrder =
                orderRepository.save(order);

        // 8. Create payment
        PaymentRequest paymentRequest =
                PaymentRequest.builder()
                        .orderId(savedOrder.getId())
                        .amount(totalAmount)
                        .paymentMethod("COD")
                        .build();

        PaymentResponse paymentResponse =
                paymentClient.createPayment(
                        paymentRequest
                );

        System.out.println(
                "Payment created : "
                        + paymentResponse.getId()
        );

        // 9. Confirm order if payment is successful
        if ("SUCCESS".equals(
                paymentResponse.getPaymentStatus()
        )) {

            savedOrder.setStatus("CONFIRMED");

            savedOrder =
                    orderRepository.save(savedOrder);
        }

        return savedOrder;
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found! with id: "
                                        + id
                        )
                );
    }

    public Order updateOrderStatus(
            Long orderId,
            String status
    ) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found with id: "
                                                + orderId
                                )
                        );

        order.setStatus(status);

        return orderRepository.save(order);
    }

    public Order updateOrderFromPayment(
            Long paymentId
    ) {

        PaymentResponse paymentResponse =
                paymentClient.getPaymentById(paymentId);

        if (!"SUCCESS".equals(
                paymentResponse.getPaymentStatus()
        )) {

            throw new RuntimeException(
                    "Payment is not successful"
            );
        }

        return updateOrderStatus(
                paymentResponse.getOrderId(),
                "CONFIRMED"
        );
    }
}

