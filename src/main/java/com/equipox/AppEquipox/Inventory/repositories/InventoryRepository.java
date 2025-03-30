package com.equipox.AppEquipox.Inventory.repositories;

import java.util.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.equipox.AppEquipox.Inventory.models.InventoryModel;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {

    public Optional<InventoryModel> findByProductName(String productName);

    @Query("SELECT i FROM InventoryModel i " +
            "JOIN FETCH i.product p " +
            "WHERE (LOWER(p.description) LIKE LOWER(CONCAT('%', :searchParam, '%')) " +
            "OR LOWER(p.code) LIKE LOWER(CONCAT('%', :searchParam, '%')) " +
            "OR LOWER(p.category) LIKE LOWER(CONCAT('%', :searchParam, '%')) " +
            "OR LOWER(p.color) LIKE LOWER(CONCAT('%', :searchParam, '%')) " +
            "OR LOWER(p.modelo) LIKE LOWER(CONCAT('%', :searchParam, '%')) " +
            "OR LOWER(i.productName) LIKE LOWER(CONCAT('%', :searchParam, '%'))) " +
            "AND i.quantity > 0")
    public List<InventoryModel> findBySearchTerm(@Param("searchParam") String searchParam);
}
