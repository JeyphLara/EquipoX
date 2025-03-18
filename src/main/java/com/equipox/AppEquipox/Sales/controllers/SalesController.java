package com.equipox.AppEquipox.Sales.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipox.AppEquipox.Customers.services.CustomerService;
import com.equipox.AppEquipox.Sales.models.SalesModel;
import com.equipox.AppEquipox.Sales.services.SalesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    private final SalesService salesService;
    private final CustomerService customerService;

    @Autowired
    public SalesController(SalesService salesService, CustomerService customerService) {
        this.salesService = salesService;
        this.customerService = customerService;
    }

    @GetMapping("/id")
    public Optional<SalesModel> getSaleById(@PathVariable Long id) {
        return this.salesService.getSaleById(id);
    }

    @PostMapping("/saveSale")
    public SalesModel saveSale(@RequestBody SalesModel salesModel) {

        try {
            long idClient = salesModel.getCustomer().getId();
            if (!customerService.getCustomerById(idClient).isPresent()) {
                customerService.createCustomer(salesModel.getCustomer());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar al cliente: " + e.getMessage());
        }

        return this.salesService.createSale(salesModel);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelInvoice(@PathVariable Long id) {
        if (this.salesService.cancelInvoice(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
