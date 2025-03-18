package com.equipox.AppEquipox.Customers.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.equipox.AppEquipox.Utils.Enums.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15, name = "document_number")
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "document_type")
    private DocumentType documentType;

    @Column(nullable = false, length = 50, name = "first_name")
    private String firstName;

    @Column(nullable = false, length = 50, name = "last_name")
    private String lastName;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 13, name = "phone_number")
    private String phoneNumber;

    @Column(nullable = true, length = 100)
    private String address;

    @Column(nullable = true, length = 50)
    private String city;

}
