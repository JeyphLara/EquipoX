package com.equipox.AppEquipox.Products.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipox.AppEquipox.Products.models.ProductModel;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByDescription(String description);
    List<ProductModel> findByCategory(String category);
    
    //ecaic

    @Query("SELECT p FROM ProductModel p WHERE " +
       "(:category IS NULL OR p.category = :category) AND " +
       "(:minPrice IS NULL OR p.vlrVenta >= :minPrice) AND " +
       "(:maxPrice IS NULL OR p.vlrVenta <= :maxPrice) AND " +
       "(:stockAvailable IS NULL OR p.stock > 0)") 

       List<ProductModel> filterProducts(
        @Param("category") String category,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice,
        @Param("stockAvailable") Boolean stockAvailable);

}
