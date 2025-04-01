package com.equipox.AppEquipox.Products.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class ReviewModel {

    //Este modelo representa las reseñas y está relacionado con ProductModel.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false)
    private int rating;         // Calificación del 1 al 5

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductModel product;
}

