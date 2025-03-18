package com.equipox.AppEquipox.Inventory.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipox.AppEquipox.Inventory.models.InventoryModel;
import com.equipox.AppEquipox.Inventory.repositories.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryModel> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryModel> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public InventoryModel saveInventory(InventoryModel inventory) {
        return inventoryRepository.save(inventory);
    }

    public InventoryModel updateInventory(Long id, InventoryModel updatedInventory) {
        return inventoryRepository.findById(id).map(inventory -> {
            inventory.setQuantity(updatedInventory.getQuantity());
            return inventoryRepository.save(inventory);
        }).orElseThrow(() -> new RuntimeException("No se encontro Inventario con ID: " + id));
    }

    public Optional<InventoryModel> findByProductName(String productName) {
        return inventoryRepository.findByProductName(productName);
    }

}
