package com.epam.liadov.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Customer - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Table
@Data
public class Customer implements CrudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int customerId;

    @Column
    private String customerName;

    @Column
    private String phone;


    public Customer() {
    }

    public Customer(String customerName, String phone) {
        this.customerName = customerName;
        this.phone = phone;
    }
}
