package com.equipox.AppEquipox.Inventory.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equipox.AppEquipox.Inventory.dto.InventoryRequest;
import com.equipox.AppEquipox.Inventory.dto.GenericResponse;
import com.equipox.AppEquipox.Inventory.models.InventoryModel;
import com.equipox.AppEquipox.Inventory.services.InventoryService;
import com.equipox.AppEquipox.Products.models.ProductModel;
import com.equipox.AppEquipox.Products.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/inventory") // Define el prefijo base para todos los endpoints de este controlador
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductService productService;

    @Autowired // Inyección de dependencias para el servicio de inventario
    public InventoryController(InventoryService inventoryService, ProductService productService) {
        this.inventoryService = inventoryService;
        this.productService = productService;
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
     * @param request Objeto InventoryRequest con los datos del inventario a crear.
     * @return El inventario creado.
     */
    @Operation(summary = "Crear un nuevo inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InventoryModel.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createInventory(@RequestBody InventoryRequest request) {

        boolean exists = inventoryService.existsByProductId(request.getProductId());
        if (exists) {
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un inventario registrado para el producto con ID: " + request.getProductId());
        }

        Optional<ProductModel> optionalProduct = productService.getProductById(request.getProductId());

        if (!optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Producto no encontrado con ID: " + request.getProductId());
        }

        ProductModel product = optionalProduct.get();

        InventoryModel inventory = new InventoryModel();
        inventory.setProduct(product);
        inventory.setProductCode(product.getCode());
        inventory.setProductName(product.getDescription());
        inventory.setQuantity(request.getQuantity());
        inventory.setLastUpdated(LocalDateTime.now());

        InventoryModel savedInventory = inventoryService.saveInventory(inventory);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedInventory);
    }

    /**
     * Endpoint para actualizar un producto existente en el inventario.
     * 
     * @param id               ID del producto a actualizar.
     * @param updatedInventory Objeto InventoryRequest con los datos actualizados.
     * @return ResponseEntity con el producto actualizado o un estado 404 si no se
     *         encuentra.
     */
    /*
     * @PutMapping("/{id}")
     * public ResponseEntity<InventoryModel> updateInventory(@PathVariable Long id,
     * 
     * @RequestBody InventoryModel updatedInventory) {
     * try {
     * InventoryModel inventory = inventoryService.updateInventory(id,
     * updatedInventory);
     * return ResponseEntity.ok(inventory);
     * } catch (RuntimeException e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

    @Operation(summary = "Actualizar inventario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@PathVariable Long id,
            @RequestBody InventoryRequest updatedInventory) {

        boolean exists = inventoryService.existsByProductId(updatedInventory.getProductId());
        if (exists) {
            Optional<ProductModel> optionalProduct = productService.getProductById(updatedInventory.getProductId());

            if (!optionalProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto no encontrado con ID: " + updatedInventory.getProductId());
            }

            ProductModel product = optionalProduct.get();

            InventoryModel inventory = inventoryService.getInventoryById(id).orElse(new InventoryModel());
            inventory.setProduct(product);
            inventory.setQuantity(updatedInventory.getQuantity());
            inventory.setLastUpdated(LocalDateTime.now());

            InventoryModel inventoryUpd = inventoryService.updateInventory(id, inventory);
            if (inventoryUpd == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontr el inventario con ID: " + id);
            }

            return ResponseEntity.ok(new GenericResponse("Inventario actualizado correctamente"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un inventario registrado para el producto con ID: "
                            + updatedInventory.getProductId());
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
