package com.equipox.AppEquipox.Sales.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equipox.AppEquipox.Sales.models.PaymentMethodModel;
import com.equipox.AppEquipox.Sales.repositories.PaymentMethodRepository;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository repository;

    public List<PaymentMethodModel> getAllMethods() {
        return repository.findAll();
    }

    public PaymentMethodModel createOrGetMethod(String methodName) {
        PaymentMethodModel existing = repository.findByMethodName(methodName);
        if (existing != null) return existing;

        PaymentMethodModel newMethod = new PaymentMethodModel();
        newMethod.setMethodName(methodName);
        return repository.save(newMethod);
    }
}
