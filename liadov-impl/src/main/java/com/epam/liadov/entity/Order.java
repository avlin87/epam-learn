package com.epam.liadov.entity;

import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Order - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Table(name = "[order]")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int orderID;

    @Expose
    private String orderNumber;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_customer"))
    @Expose
    private int customerId;

    @Temporal(TemporalType.TIMESTAMP)
    @Expose
    private Date orderDate;

    @Expose
    private BigDecimal totalAmount;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "orderProduct",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    @Expose(deserialize = false)
    private List<Product> productId = new ArrayList<>();
}