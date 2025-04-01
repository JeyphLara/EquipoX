package com.equipox.AppEquipox.Sales.models;

import java.time.LocalDateTime;
import java.util.List;

import com.equipox.AppEquipox.Customers.models.CustomerModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
public class SalesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerModel customer;

    @Column(nullable = false)
    private LocalDateTime saleDate;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SaleDetailModel> saleDetails;

    @Column(nullable = false)
    private Float totalAmount;

    @Column
    private String status;
    /*
     * @Column(nullable = false)
     * private PaymentMethod paymentMethod;
     */

}
