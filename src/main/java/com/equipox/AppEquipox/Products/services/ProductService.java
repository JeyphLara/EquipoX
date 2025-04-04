package com.equipox.AppEquipox.Products.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipox.AppEquipox.Products.models.ProductModel;
import com.equipox.AppEquipox.Products.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductModel> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductModel> getProductByDescription(String description) {
        return productRepository.findByDescription(description);
    }


    //listar productos por categoría
    public List<ProductModel> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
