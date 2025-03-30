package com.equipox.AppEquipox.Inventory.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/searchName")
    public ResponseEntity<InventoryModel> getInventoryById(@RequestParam String productName) {
        Optional<InventoryModel> inventory = inventoryService.findByProductName(productName);
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

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchProducts(
            @RequestParam String searchTerm) {

        List<InventoryModel> inventoryList = inventoryService.searchProducts(searchTerm);

        // Transformar la respuesta para devolver solo los datos necesarios
        List<Map<String, Object>> response = inventoryList.stream().map(inventory -> {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("code", inventory.getProductCode());
            productInfo.put("description", inventory.getProduct().getDescription());
            productInfo.put("price", inventory.getProduct().getVlrVenta());
            productInfo.put("stock", inventory.getQuantity());
            productInfo.put("category", inventory.getProduct().getCategory());
            return productInfo;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
