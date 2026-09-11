package com.example.productservice.service;


import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //Methods
    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + id));
    }

    public Product updateProduct(Long id, Product productDetails){
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());

        return productRepository.save(product);

    }

    public void deleteProduct(Long id){

        Product product = productRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException("Product not found with id " + id));


        productRepository.delete(product);
    }

    public Product updateStock(Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + productId)
                );

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);

        return productRepository.save(product);
    }
}
