package com.equipox.AppEquipox.Sales.controllers;

import com.equipox.AppEquipox.Sales.models.PaymentMethodModel;
import com.equipox.AppEquipox.Sales.services.PaymentMethodService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public List<PaymentMethodModel> getAllPaymentMethods() {
        return paymentMethodService.getAllMethods();
    }
}
