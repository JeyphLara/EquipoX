package com.equipox.AppEquipox.Sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.equipox.AppEquipox.Sales.models.SalesModel;

import jakarta.transaction.Transactional;

@Repository
public interface SalesRepository extends JpaRepository<SalesModel, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE SalesModel s SET s.status = 'ANULADA' WHERE s.id = :id")
    int cancelInvoice(@Param("id") Long id);
}
