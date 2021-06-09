package com.epam.liadov.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ProductDto - class for api representation of Product entity
 *
 * @author Aleksandr Liadov
 */
@Data
@Accessors(chain = true)
public class ProductDto {

    @NotNull
    private int productId;

    private String productName;

    private int supplierId;

    private BigDecimal unitPrice;

    private boolean isDiscontinued;
}
