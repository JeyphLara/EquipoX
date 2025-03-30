package com.equipox.AppEquipox.Customers.controllers;

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

import com.equipox.AppEquipox.Customers.models.CustomerModel;
import com.equipox.AppEquipox.Customers.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllCustomers() {
        List<CustomerModel> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerModel> getCustomerById(@PathVariable Long id) {
        Optional<CustomerModel> customer = this.customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CustomerModel> getCustomerByName(@PathVariable String name) {
        Optional<CustomerModel> customer = this.customerService.getFindByName(name);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<CustomerModel> getFindByDocumentNumber(@PathVariable String documentNumber) {
        Optional<CustomerModel> customer = this.customerService.getFindByDocumentNumber(documentNumber);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(customer.get());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerModel customer) {
        try {
            CustomerModel newCustomer = this.customerService.createCustomer(customer);
            return ResponseEntity.ok(newCustomer);
        } catch (RuntimeException e) {
           
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id,
            @RequestBody CustomerModel customerDetails) {
        try {
            CustomerModel updatedCustomer = this.customerService.updateCustomer(id, customerDetails);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
