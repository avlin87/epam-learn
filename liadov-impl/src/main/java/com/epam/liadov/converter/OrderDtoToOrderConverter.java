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
 * OrderDtoToOrderConverter - class converter form OrderDto to Order
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class OrderDtoToOrderConverter implements Converter<OrderDto, Order> {

    @Override
    public Order convert(@NotNull OrderDto orderDto) {
        var order = new Order();
        int orderID = orderDto.getOrderID();
        String orderNumber = orderDto.getOrderNumber();
        int customerId = orderDto.getCustomerId();
        Date orderDate = orderDto.getOrderDate();
        BigDecimal totalAmount = orderDto.getTotalAmount();
        List<Integer> productIds = orderDto.getProductId();

        List<Product> products = productIds.stream()
                .map((x) -> {
                    var product = new Product();
                    product.setProductId(x);
                    return product;
                })
                .collect(Collectors.toList());

        order.setOrderID(orderID)
                .setOrderNumber(orderNumber)
                .setCustomerId(customerId)
                .setOrderDate(orderDate)
                .setTotalAmount(totalAmount)
                .setProductId(products);
        log.trace("convert() - convert from '{}' to {}", orderDto, order);
        return order;
    }
}
