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
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    private String orderNumber;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_order_customer"))
    private int customerId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private BigDecimal totalAmount;

}