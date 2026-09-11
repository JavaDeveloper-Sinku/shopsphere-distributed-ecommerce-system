package com.example.orderservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long userId;
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
