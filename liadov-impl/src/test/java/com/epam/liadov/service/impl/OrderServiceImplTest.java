package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.exception.NoContentException;
import com.epam.liadov.repository.OrderRepository;
import com.epam.liadov.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OrderServiceImplTest - test for {@link OrderServiceImpl}
 *
 * @author Aleksandr Liadov
 */
@DataJpaTest
@RunWith(SpringRunner.class)
class OrderServiceImplTest {

    private final EntityFactory factory = new EntityFactory();

    @Autowired
    private OrderService orderService;

    @Test
    void saveReturnOrder() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);

        Order saveResult = orderService.save(testOrder);

        assertEquals(testOrder, saveResult);
    }

    @Test
    void updateReturnOrder() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);
        testOrder = orderService.save(testOrder);
        testOrder.setTotalAmount(BigDecimal.valueOf(12345)).setOrderNumber("Updated Order");

        Order updateResult = orderService.update(testOrder);

        assertEquals(testOrder, updateResult);
    }

    @Test
    void updateThrowNoContentException() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);
        testOrder = orderService.save(testOrder);
        int testOrderId = testOrder.getOrderID() + 999;
        testOrder.setOrderID(testOrderId);
        Order finalTestOrder = testOrder;

        Executable executeUpdate = () -> orderService.update(finalTestOrder);

        assertThrows(NoContentException.class, executeUpdate);
    }

    @Test
    void findReturnOrder() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);
        testOrder = orderService.save(testOrder);
        int testOrderId = testOrder.getOrderID();

        Order foundOrder = orderService.find(testOrderId);

        assertEquals(testOrder, foundOrder);
    }

    @Test
    void findThrowNoContentException() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);
        testOrder = orderService.save(testOrder);
        int testOrderId = testOrder.getOrderID() + 999;

        Executable executeUpdate = () -> orderService.find(testOrderId);

        assertThrows(NoContentException.class, executeUpdate);
    }

    @Test
    void deleteReturnTrue() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);
        testOrder = orderService.save(testOrder);
        int testOrderId = testOrder.getOrderID();

        boolean deleteResult = orderService.delete(testOrderId);

        assertTrue(deleteResult);
    }

    @Test
    void deleteThrowNoContentException() {
        Customer testCustomer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(testCustomer);
        testOrder = orderService.save(testOrder);
        int testOrderId = testOrder.getOrderID();

        Executable executeDelete = () -> orderService.delete(testOrderId + 999);

        assertThrows(NoContentException.class, executeDelete);
    }

    @Test
    void getAllReturnList() {
        List<Order> all = orderService.getAll();

        assertNotNull(all);
    }

    @Test
    void findByCustomerId() {
        List<Order> byCustomerId = orderService.findByCustomerId(1);

        assertNotNull(byCustomerId);
    }

    @TestConfiguration
    static class MyTestConfiguration {
        @Bean
        public OrderService orderService(OrderRepository repository) {
            return new OrderServiceImpl(repository);
        }
    }
}