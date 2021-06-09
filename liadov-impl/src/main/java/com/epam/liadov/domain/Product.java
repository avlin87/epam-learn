package com.epam.liadov.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Product - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Data
@Accessors(chain = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_product_supplier"))
    private int supplierId;

    private BigDecimal unitPrice;

    private boolean isDiscontinued;

    @ManyToMany(mappedBy = "productId", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Transient
    private List<Order> orderList = new ArrayList<>();
}
