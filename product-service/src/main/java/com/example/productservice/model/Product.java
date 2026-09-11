package com.example.productservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Product price is required")
    @PositiveOrZero(message = "Product price cannot be negative")
    private Double price;

    @NotNull(message = "Product stock is required")
    @Min(value = 0, message = "Product stock cannot be negative")
    private Integer stock;

}
