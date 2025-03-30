package com.equipox.AppEquipox.Customers.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equipox.AppEquipox.Customers.models.CustomerModel;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    public Optional<CustomerModel> findByFirstNameOrLastName(String firstName, String lastName);

    boolean existsBydocumentNumber(String documentNumber);

    public Optional<CustomerModel> findBydocumentNumber(String documentNumber);
}
