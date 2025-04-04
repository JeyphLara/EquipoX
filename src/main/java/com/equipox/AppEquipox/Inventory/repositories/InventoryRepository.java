package com.equipox.AppEquipox.Inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipox.AppEquipox.Inventory.models.InventoryModel;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {

}
