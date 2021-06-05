package com.epam.liadov.service.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderProductRepository;
import com.epam.liadov.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * OrderServiceImplTest - test for {@link OrderServiceImpl}
 *
 * @author Aleksandr Liadov
 */
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;
    private EntityFactory factory;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnTrue() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        List<Integer> productId = List.of(1, 2);
        order.setProductId(productId);
        when(orderRepository.save(order)).thenReturn(Optional.ofNullable(order));
        when(orderProductRepository.saveId(order.getOrderID(), productId)).thenReturn(true);

        boolean saveResult = orderServiceImpl.save(order);

        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        List<Integer> productId = List.of(1, 2);
        order.setProductId(productId);
        when(orderRepository.update(any())).thenReturn(Optional.ofNullable(order));
        when(orderProductRepository.updateId(order.getOrderID(), productId)).thenReturn(true);

        boolean saveResult = orderServiceImpl.update(order);

        assertTrue(saveResult);
    }

    @Test
    void findReturnNotNull() {
        Customer customer = factory.generateTestCustomer();
        Order expectedValue = factory.generateTestOrder(customer);
        when(orderRepository.find(anyInt())).thenReturn(Optional.ofNullable(expectedValue));

        Order order = orderServiceImpl.find(1);

        assertNotNull(order);
    }

    @Test
    void delete() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        when(orderRepository.delete(any())).thenReturn(true);

        boolean deleteResult = orderServiceImpl.delete(order);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnList() {
        List<Order> all = orderServiceImpl.getAll();

        assertNotNull(all);
    }

    @Test
    void findByCustomerId() {
        List<Order> byCustomerId = orderServiceImpl.findByCustomerId(1);

        assertNotNull(byCustomerId);
    }
}