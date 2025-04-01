package com.equipox.AppEquipox.Products.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equipox.AppEquipox.Products.models.ProductModel;
import com.equipox.AppEquipox.Products.services.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductModel> getProductById(@PathVariable Long id) {
        Optional<ProductModel> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(product.get());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductModel>> searchProducts(@RequestParam String name) {
        List<ProductModel> products = productService.getProductByDescription(name);
        return ResponseEntity.ok(products);

    }

    //edww
    @GetMapping("/filter")
public ResponseEntity<List<ProductModel>> filterProducts(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice,
        @RequestParam(required = false) Boolean stockAvailable) {

    List<ProductModel> filteredProducts = productService.filterProducts(category, minPrice, maxPrice, stockAvailable);

    if (filteredProducts.isEmpty()) {
        return ResponseEntity.noContent().build();
    }



    return ResponseEntity.ok(filteredProducts);
}

    //listar productos por categoría (con endpoints)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductModel>> getProductsByCategory(@PathVariable String category) {
        List<ProductModel> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
}
