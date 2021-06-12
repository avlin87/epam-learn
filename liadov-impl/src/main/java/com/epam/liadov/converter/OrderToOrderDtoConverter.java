package com.epam.liadov.converter;


import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StringToRequestConverter - class converter form Order to OrderDto
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class OrderToOrderDtoConverter implements Converter<Order, OrderDto> {

    @Override
    public OrderDto convert(@NotNull Order order) {
        var orderDto = new OrderDto();
        int orderID = order.getOrderID();
        String orderNumber = order.getOrderNumber();
        int customerId = order.getCustomerId();
        Date orderDate = order.getOrderDate();
        BigDecimal totalAmount = order.getTotalAmount();
        List<Product> products = order.getProductId();

        List<Integer> productIds = products.stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        orderDto.setOrderID(orderID)
                .setOrderNumber(orderNumber)
                .setCustomerId(customerId)
                .setOrderDate(orderDate)
                .setTotalAmount(totalAmount)
                .setProductId(productIds);
        log.info("convert() - convert from '{}' to {}", order, orderDto);
        return orderDto;
    }
}