package com.epam.liadov.converter;

import com.epam.liadov.domain.Order;
import com.epam.liadov.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * OrderToOrderDtoConverterTest - test for {@link OrderToOrderDtoConverter}
 *
 * @author Aleksandr Liadov
 */
class OrderToOrderDtoConverterTest {
    private OrderToOrderDtoConverter toOrderDtoConverter;

    @BeforeEach
    public void setup() {
        toOrderDtoConverter = new OrderToOrderDtoConverter();
    }

    @Test
    void convert() {
        var order = new Order();
        order.setOrderID(1)
                .setOrderNumber("orderNumber")
                .setCustomerId(1)
                .setOrderDate(new Date())
                .setTotalAmount(BigDecimal.TEN);

        OrderDto orderDto = toOrderDtoConverter.convert(order);

        assertEquals(order.getOrderID(), orderDto.getOrderID());
        assertEquals(order.getOrderNumber(), orderDto.getOrderNumber());
        assertEquals(order.getCustomerId(), orderDto.getCustomerId());
        assertEquals(order.getOrderDate(), orderDto.getOrderDate());
        assertEquals(order.getTotalAmount(), orderDto.getTotalAmount());
    }
}