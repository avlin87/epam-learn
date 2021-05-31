package com.epam.liadov.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Product - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Data
@Component
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_supplier"))
    private int supplierId;

    private BigDecimal unitPrice;

    private boolean isDiscontinued;

}
