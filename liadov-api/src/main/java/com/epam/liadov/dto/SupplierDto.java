package com.epam.liadov.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * SupplierDto - class for api representation of Supplier entity
 *
 * @author Aleksandr Liadov
 */
@Data
@Accessors(chain = true)
public class SupplierDto {

    private int supplierId;

    private String companyName;

    private String phone;
}