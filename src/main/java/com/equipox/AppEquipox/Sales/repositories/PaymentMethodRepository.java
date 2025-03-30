package com.equipox.AppEquipox.Sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.equipox.AppEquipox.Sales.models.PaymentMethodModel;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodModel, Long> {
    PaymentMethodModel findByMethodName(String methodName);
}
