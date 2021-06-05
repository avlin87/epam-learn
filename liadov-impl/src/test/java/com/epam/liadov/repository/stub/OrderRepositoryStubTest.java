package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * OrderRepositoryStubTest - test for {@link OrderRepositoryStub}
 *
 * @author Aleksandr Liadov
 */
class OrderRepositoryStubTest {
    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private OrderRepositoryStub orderRepository;

    private EntityFactory factory;
    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        customer = factory.generateTestCustomer();
        when(factoryTest.generateTestOrder(any())).thenReturn(factory.generateTestOrder(customer));
        when(factoryTest.generateTestCustomer()).thenReturn(customer);
    }

    @Test
    void saveReturnOptionalWithOrder() {
        Order order = factory.generateTestOrder(customer);

        Optional<Order> optionalOrder = orderRepository.save(order);

        boolean saveResult = optionalOrder.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnOptionalWithOrder() {
        Order order = factory.generateTestOrder(customer);

        Optional<Order> optionalOrder = orderRepository.update(order);

        boolean updateResult = optionalOrder.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Order> optionalOrder = orderRepository.find(0);

        assertTrue(optionalOrder.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Order order = factory.generateTestOrder(customer);
        boolean deleteResult = orderRepository.delete(order);

        assertTrue(deleteResult);
    }

    @Test
    void getOrdersByCustomerIdReturnListOfObjects() {
        List<Order> orderList = orderRepository.getOrdersByCustomerId(1);

        assertFalse(orderList.isEmpty());
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Order> all = orderRepository.getAll();

        assertFalse(all.isEmpty());
    }
}