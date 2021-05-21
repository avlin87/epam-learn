package com.epam.liadov.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Product - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Table
@Data
public class Product implements CrudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int productId;

    @Column
    private String productName;

    @Column
    private int supplierId;

    @Column
    private BigDecimal unitPrice;

    @Column
    private boolean isDiscontinued;

    public Product() {
    }

    public Product(String productName, int supplierId, BigDecimal unitPrice, boolean isDiscontinued) {
        this.productName = productName;
        this.supplierId = supplierId;
        this.unitPrice = unitPrice;
        this.isDiscontinued = isDiscontinued;
    }

}
