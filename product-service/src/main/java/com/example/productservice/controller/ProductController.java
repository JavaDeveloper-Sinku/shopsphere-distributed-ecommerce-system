package com.example.productservice.controller;


import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok(productService.addProduct(product));

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());

    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
        return ResponseEntity.ok(productService.updateProduct(id, product));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
