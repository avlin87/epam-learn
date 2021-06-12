package com.epam.liadov.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Supplier - POJO class represented in database
 *
 * @author Aleksandr Liadov
 */
@Entity
@Data
@Accessors(chain = true)
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;

    private String companyName;

    private String phone;
}
