package com.ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carrito_items")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Carrito
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    // Relación con Producto
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private Integer cantidad;

    private Double subtotal; // cantidad * precio
}
