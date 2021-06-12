package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * OrderDtoToOrderConverterTest - test for {@link OrderDtoToOrderConverter}
 *
 * @author Aleksandr Liadov
 */
class OrderDtoToOrderConverterTest {

    private OrderDtoToOrderConverter toOrderConverter;

    @BeforeEach
    public void setup() {
        toOrderConverter = new OrderDtoToOrderConverter();
    }

    @Test
    void convert() {
        var orderDto = new OrderDto();
        orderDto.setOrderID(1)
                .setOrderNumber("orderNumber")
                .setCustomerId(1)
                .setOrderDate(new Date())
                .setTotalAmount(BigDecimal.TEN);

        Order order = toOrderConverter.convert(orderDto);

        assertEquals(orderDto.getOrderID(), order.getOrderID());
        assertEquals(orderDto.getOrderNumber(), order.getOrderNumber());
        assertEquals(orderDto.getCustomerId(), order.getCustomerId());
        assertEquals(orderDto.getOrderDate(), order.getOrderDate());
        assertEquals(orderDto.getTotalAmount(), order.getTotalAmount());
    }
}