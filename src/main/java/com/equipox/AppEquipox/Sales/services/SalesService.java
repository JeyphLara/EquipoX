package com.equipox.AppEquipox.Sales.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipox.AppEquipox.Sales.models.SalesModel;
import com.equipox.AppEquipox.Sales.repositories.SalesRepository;

import jakarta.transaction.Transactional;

@Service
public class SalesService {

    private final SalesRepository salesRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Transactional
    public SalesModel createSale(SalesModel sale) {
        return salesRepository.save(sale);
    }

    public Optional<SalesModel> getSaleById(Long id) {
        return salesRepository.findById(id);
    }

    @Transactional
    public Boolean cancelInvoice(Long id) {
        return salesRepository.cancelInvoice(id) == 1;
    }
}
