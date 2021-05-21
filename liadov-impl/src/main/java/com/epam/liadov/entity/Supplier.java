package com.epam.liadov.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Supplier - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Table
@Data
public class Supplier implements CrudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int supplierId;

    @Column
    private String companyName;

    @Column
    private String phone;

    public Supplier() {
    }

    public Supplier(String companyName, String phone) {
        this.companyName = companyName;
        this.phone = phone;
    }
}
