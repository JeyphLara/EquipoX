package com.equipox.AppEquipox.Inventory.Controllers;

import com.equipox.AppEquipox.Inventory.controllers.InventoryController;
import com.equipox.AppEquipox.Inventory.models.InventoryModel;
import com.equipox.AppEquipox.Inventory.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllInventory() {
        // Arrange
        List<InventoryModel> mockInventoryList = Arrays.asList(
                new InventoryModel(1L, "P001", "Product 1", 10, LocalDateTime.now(), null),
                new InventoryModel(2L, "P002", "Product 2", 20, LocalDateTime.now(), null));
        when(inventoryService.getAllInventory()).thenReturn(mockInventoryList);

        // Act
        List<InventoryModel> result = inventoryController.getAllInventory();
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(inventoryService, times(1)).getAllInventory();
    }

    @Test
    void testGetInventoryById_Found() {
        // Arrange
        InventoryModel mockInventory = new InventoryModel(1L, "P001", "Product 1", 10, LocalDateTime.now(), null);
        when(inventoryService.getInventoryById(1L)).thenReturn(Optional.of(mockInventory));

        // Act
        ResponseEntity<InventoryModel> response = inventoryController.getInventoryById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockInventory, response.getBody());
        verify(inventoryService, times(1)).getInventoryById(1L);
    }

    @Test
    void testGetInventoryById_NotFound() {
        // Arrange
        when(inventoryService.getInventoryById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<InventoryModel> response = inventoryController.getInventoryById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(inventoryService, times(1)).getInventoryById(1L);
    }

    @Test
    void testCreateInventory() {
        // Arrange
        InventoryModel mockInventory = new InventoryModel(1L, "P001", "Product 1", 10, LocalDateTime.now(), null);
        when(inventoryService.saveInventory(mockInventory)).thenReturn(mockInventory);

        // Act
        InventoryModel result = inventoryController.createInventory(mockInventory);

        // Assert
        assertNotNull(result);
        assertEquals(mockInventory, result);
        verify(inventoryService, times(1)).saveInventory(mockInventory);
    }

    @Test
    void testUpdateInventory_Success() {
        // Arrange
        InventoryModel updatedInventory = new InventoryModel(1L, "P001", "Updated Product", 15, LocalDateTime.now(),
                null);
        when(inventoryService.updateInventory(eq(1L), any(InventoryModel.class))).thenReturn(updatedInventory);

        // Act
        ResponseEntity<InventoryModel> response = inventoryController.updateInventory(1L, updatedInventory);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedInventory, response.getBody());
        verify(inventoryService, times(1)).updateInventory(eq(1L), any(InventoryModel.class));
    }

    @Test
    void testUpdateInventory_NotFound() {
        // Arrange
        when(inventoryService.updateInventory(eq(1L), any(InventoryModel.class)))
                .thenThrow(new RuntimeException("Not Found"));

        // Act
        ResponseEntity<InventoryModel> response = inventoryController.updateInventory(1L, new InventoryModel());

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(inventoryService, times(1)).updateInventory(eq(1L), any(InventoryModel.class));
    }

    @Test
    void testSearchProducts() {
        // Arrange
        List<InventoryModel> mockInventoryList = Arrays.asList(
                new InventoryModel(1L, "P001", "Product 1", 10, LocalDateTime.now(), null),
                new InventoryModel(2L, "P002", "Product 2", 20, LocalDateTime.now(), null));
        when(inventoryService.searchProducts("Product")).thenReturn(mockInventoryList);

        // Act
        ResponseEntity<List<Map<String, Object>>> response = inventoryController.searchProducts("Product");

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(inventoryService, times(1)).searchProducts("Product");
    }
}
