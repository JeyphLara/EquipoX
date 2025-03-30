package com.equipox.AppEquipox.Customers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipox.AppEquipox.Customers.models.CustomerModel;
import com.equipox.AppEquipox.Customers.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Obtener todos los clientes
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Buscar un cliente por ID
    public Optional<CustomerModel> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Crear un nuevo cliente
    @Transactional
    public CustomerModel createCustomer(CustomerModel customer) {
        if (customerRepository.existsBydocumentNumber(customer.getDocumentNumber())) {
            throw new RuntimeException("Ya existe un cliente con esta cédula");
        }
        return customerRepository.save(customer);
    }

    // Actualizar un cliente existente
    @Transactional
    public CustomerModel updateCustomer(Long id, CustomerModel customerDetails) {
        return customerRepository.findById(id).map(customer -> {
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setEmail(customerDetails.getEmail());
            customer.setDocumentNumber(customerDetails.getDocumentNumber());
            customer.setDocumentType(customerDetails.getDocumentType());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    public Optional<CustomerModel> getFindByDocumentNumber(String documentNumber) {
        return customerRepository.findBydocumentNumber(documentNumber);
    }

    public Optional<CustomerModel> getFindByName(String name) {
        return customerRepository.findByFirstNameOrLastName(name, name);
    }
}
