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
@RequestMapping("/api/inventory") // Define el prefijo base para todos los endpoints de este controlador
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired // Inyección de dependencias para el servicio de inventario
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Endpoint para obtener todos los productos del inventario.
     * 
     * @return Lista de todos los productos en el inventario.
     */
    @GetMapping
    public List<InventoryModel> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    /**
     * Endpoint para obtener un producto del inventario por su ID.
     * 
     * @param id ID del producto a buscar.
     * @return ResponseEntity con el producto encontrado o un estado 404 si no
     *         existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventoryModel> getInventoryById(@PathVariable Long id) {
        Optional<InventoryModel> inventory = inventoryService.getInventoryById(id);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para buscar un producto en el inventario por su nombre.
     * 
     * @param productName Nombre del producto a buscar.
     * @return ResponseEntity con el producto encontrado o un estado 404 si no
     *         existe.
     */
    @GetMapping("/searchName")
    public ResponseEntity<InventoryModel> getInventoryById(@RequestParam String productName) {
        Optional<InventoryModel> inventory = inventoryService.findByProductName(productName);
        return inventory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para crear un nuevo producto en el inventario.
     * 
     * @param inventory Objeto InventoryModel con los datos del producto a crear.
     * @return El producto creado.
     */
    @PostMapping
    public InventoryModel createInventory(@RequestBody InventoryModel inventory) {
        return inventoryService.saveInventory(inventory);
    }

    /**
     * Endpoint para actualizar un producto existente en el inventario.
     * 
     * @param id               ID del producto a actualizar.
     * @param updatedInventory Objeto InventoryModel con los datos actualizados.
     * @return ResponseEntity con el producto actualizado o un estado 404 si no se
     *         encuentra.
     */
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

    /**
     * Endpoint para buscar productos en el inventario utilizando un término de
     * búsqueda.
     * 
     * @param searchTerm Término de búsqueda que puede coincidir con varios campos
     *                   del producto.
     * @return ResponseEntity con una lista de productos que coinciden con el
     *         término de búsqueda.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchProducts(
            @RequestParam String searchTerm) {

        // Obtiene la lista de productos que coinciden con el término de búsqueda
        List<InventoryModel> inventoryList = inventoryService.searchProducts(searchTerm);

        // Transforma la lista de productos para devolver solo los datos necesarios
        List<Map<String, Object>> response = inventoryList.stream().map(inventory -> {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("code", inventory.getProductCode()); // Código del producto
            productInfo.put("description", inventory.getProduct().getDescription()); // Descripción del producto
            productInfo.put("price", inventory.getProduct().getVlrVenta()); // Precio de venta
            productInfo.put("stock", inventory.getQuantity()); // Cantidad en inventario
            productInfo.put("category", inventory.getProduct().getCategory()); // Categoría del producto
            return productInfo;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
