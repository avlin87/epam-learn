package com.epam.liadov.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Order - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Table(name = "[order]")
@Data
public class Order implements CrudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int orderID;

    @Column
    private String orderNumber;

    @Column
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_customer"))
    private int customerId;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column
    private BigDecimal totalAmount;

    public Order() {
    }

    public Order(String orderNumber, int customerId, Date orderDate, BigDecimal totalAmount) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }
}