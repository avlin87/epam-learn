package com.epam.liadov.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OrderDto - class for api representation of Order entity
 *
 * @author Aleksandr Liadov
 */
@Data
@Accessors(chain = true)
public class OrderDto {

    private int orderID;

    private String orderNumber;

    private int customerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy, hh:mm:ss aa")
    private Date orderDate;

    private BigDecimal totalAmount;

    private List<Integer> productId = new ArrayList<>();
}
