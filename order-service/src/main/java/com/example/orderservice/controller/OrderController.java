package com.example.orderservice.controller;


import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping("/{productId}/{quantity}")
    public Order placeOrder(@PathVariable Long productId, @PathVariable int quantity) {
        return orderService.placeOrder(productId, quantity);
    }

    @GetMapping("/test/{productId}")
    public String testFeign(@PathVariable Long productId) {
        ProductResponse product = orderService.getProductByIdUsingFeign(productId);
        return "Product fetched via Feign: " + product.getName();

    }

    //method

    @PostMapping
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrder() {
        return orderService.getAllOrders();

    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
