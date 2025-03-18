package com.equipox.AppEquipox.Inventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipox.AppEquipox.Inventory.models.InventoryModel;
import com.equipox.AppEquipox.Inventory.services.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryModel> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryModel> getInventoryById(@PathVariable Long id) {
        Optional<InventoryModel> inventory = inventoryService.getInventoryById(id);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public InventoryModel createInventory(@RequestBody InventoryModel inventory) {
        return inventoryService.saveInventory(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryModel> updateInventory(@PathVariable Long id,
            @RequestBody InventoryModel updatedInventory) {
        try {
            InventoryModel inventory = inventoryService.updateInventory(id, updatedInventory);
            return ResponseEntity.ok(inventory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
