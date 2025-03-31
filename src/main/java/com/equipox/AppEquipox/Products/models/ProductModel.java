package com.equipox.AppEquipox.Products.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2, name = "vlr_bruto")
    private BigDecimal vlrBruto;

    @Column(nullable = false, precision = 5, scale = 2, name = "p_iva")
    private BigDecimal pIva;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(nullable = false, precision = 10, scale = 2, name = "vlr_venta")
    private BigDecimal vlrVenta;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    //variables complemntarias, para subsanar la HU-9 "Visualizar detalles del producto".
    @Column(nullable = true, length = 255)
    private String imageUrl;                        //para almacenar la url de la imagen el producto

    @Column(nullable = true, length = 500)
    private String materials;                       //Materiales

    @Column(nullable = true, length = 500)
    private String functionalities;                 //Funcionalidades

    // @Column(nullable = false)
    // private int stock;                           //Stock disponible

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)   //Relaciona las reseñas con un producto.
    private List<ReviewModel> reviews;              //Relación con reseñas


    @ManyToMany         //permite vincular productos relacionados en una tabla intermedia related_products.
    @JoinTable(
        name = "related_products",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "related_product_id")
    )
    private List<ProductModel> relatedProducts;      //Relación con productos recomendados


    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
