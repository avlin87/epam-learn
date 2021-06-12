package com.epam.liadov.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * CustomerDto - class for api representation of Customer entity
 *
 * @author Aleksandr Liadov
 */
@Data
@Accessors(chain = true)
public class CustomerDto {

    private int customerId;

    private String customerName;

    private String phone;
}