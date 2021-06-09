package com.epam.liadov.service.stub;

import com.epam.liadov.domain.Customer;
import com.epam.liadov.domain.Order;
import com.epam.liadov.domain.factory.EntityFactory;
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
 * OrderServiceStubTest - test for {@link OrderServiceStub}
 *
 * @author Aleksandr Liadov
 */
class OrderServiceStubTest {
    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private OrderServiceStub orderServiceStub;

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

        Optional<Order> optionalOrder = Optional.ofNullable(orderServiceStub.save(order));

        boolean saveResult = optionalOrder.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnOptionalWithOrder() {
        Order order = factory.generateTestOrder(customer);

        Optional<Order> optionalOrder = Optional.ofNullable(orderServiceStub.update(order));

        boolean updateResult = optionalOrder.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Order> optionalOrder = Optional.ofNullable(orderServiceStub.find(0));

        assertTrue(optionalOrder.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Order order = factory.generateTestOrder(customer);
        int orderID = order.getOrderID();

        boolean deleteResult = orderServiceStub.delete(orderID);

        assertTrue(deleteResult);
    }

    @Test
    void getOrdersByCustomerIdReturnListOfObjects() {
        List<Order> orderList = orderServiceStub.findByCustomerId(1);

        assertFalse(orderList.isEmpty());
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Order> all = orderServiceStub.getAll();

        assertFalse(all.isEmpty());
    }
}