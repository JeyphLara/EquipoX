package com.equipox.AppEquipox.Products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipox.AppEquipox.Products.models.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByDescription(String description);
    List<ProductModel> findByCategory(String category);

}
