package com.example.orderservice.client;


import com.example.orderservice.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id );

    @PutMapping("/api/products/{id}/stock")
    ProductResponse updateStock(
            @PathVariable("id") Long id,
            @RequestParam("quantity") int quantity
    );



}
