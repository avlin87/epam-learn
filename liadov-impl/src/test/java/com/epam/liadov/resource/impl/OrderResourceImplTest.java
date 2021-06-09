package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.OrderDtoToOrderConverter;
import com.epam.liadov.converter.OrderToOrderDtoConverter;
import com.epam.liadov.domain.Customer;
import com.epam.liadov.domain.Order;
import com.epam.liadov.domain.factory.EntityFactory;
import com.epam.liadov.dto.OrderDto;
import com.epam.liadov.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * OrderResourceImplTest - test for {@link OrderResourceImpl}
 *
 * @author Aleksandr Liadov
 */
class OrderResourceImplTest {

    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private OrderToOrderDtoConverter orderToOrderDtoConverter;
    @Mock
    private OrderDtoToOrderConverter orderDtoToOrderConverter;

    private EntityFactory factory;
    private OrderToOrderDtoConverter toOrderDtoConverter = new OrderToOrderDtoConverter();

    @InjectMocks
    private OrderResourceImpl orderResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void getOrder() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderDto testOrderDto = toOrderDtoConverter.convert(testOrder);
        when(orderService.find(anyInt())).thenReturn(testOrder);
        when(orderToOrderDtoConverter.convert(any())).thenReturn(testOrderDto);

        OrderDto orderDto = orderResource.getOrder(1);

        assertEquals(testOrderDto, orderDto);
    }

    @Test
    void addOrder() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderDto testOrderDto = toOrderDtoConverter.convert(testOrder);
        when(orderToOrderDtoConverter.convert(any())).thenReturn(testOrderDto);
        when(orderDtoToOrderConverter.convert(any())).thenReturn(testOrder);
        when(orderService.save(any())).thenReturn(testOrder);

        OrderDto orderDto = orderResource.addOrder(testOrderDto);

        assertEquals(testOrderDto, orderDto);
    }

    @Test
    void deleteOrder() {
        when(orderService.delete(anyInt())).thenReturn(true);

        orderResource.deleteOrder(1);
    }

    @Test
    void updateOrder() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderDto testOrderDto = toOrderDtoConverter.convert(testOrder);
        when(orderService.update(any())).thenReturn(testOrder);
        when(orderToOrderDtoConverter.convert(any())).thenReturn(testOrderDto);

        OrderDto updateOrder = orderResource.updateOrder(testOrderDto);

        assertEquals(testOrderDto, updateOrder);
    }

    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderService.getAll()).thenReturn(orders);

        List<OrderDto> orderList = orderResource.getAllOrders();

        assertNotNull(orderList);
    }
}