package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * OrderRepositoryTest test for {@link OrderRepository}
 *
 * @author Aleksandr Liadov
 */
class OrderRepositoryTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Order> typedQuery;

    @InjectMocks
    private OrderRepository orderRepository;

    private EntityFactory factory;
    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        customer = factory.generateTestCustomer();
    }

    @Test
    void saveReturnsTrue() {
        Order testOrder = factory.generateTestOrder(customer);

        boolean saveResult = orderRepository.save(testOrder);

        assertTrue(saveResult);
    }

    @Test
    void saveReturnsFalse() {
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        boolean saveResult = orderRepository.save(null);

        assertFalse(saveResult);
    }

    @Test
    void updateReturnThue() {
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.find(Order.class, testOrder.getOrderID())).thenReturn(testOrder);

        boolean orderUpdated = orderRepository.update(testOrder);

        assertTrue(orderUpdated);
    }

    @Test
    void updateReturnFalse() {
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.merge(testOrder)).thenThrow(TransactionRequiredException.class);

        boolean customerUpdated = orderRepository.update(testOrder);

        assertFalse(customerUpdated);
    }

    @Test
    void updateReturnFalseDueToException() {
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.find(Order.class, testOrder.getOrderID())).thenReturn(testOrder);
        when(entityManagerMock.merge(testOrder)).thenThrow(TransactionRequiredException.class);

        boolean customerUpdated = orderRepository.update(testOrder);

        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullOrder() {
        Executable executable = () -> orderRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Order> optionalOrder = orderRepository.find(0);

        assertFalse(optionalOrder.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Order.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Order> optionalOrder = orderRepository.find(1);

        assertFalse(optionalOrder.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Order testOrder = factory.generateTestOrder(customer);

        boolean deleteResult = orderRepository.delete(testOrder);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        Order testOrder = factory.generateTestOrder(customer);
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = orderRepository.delete(testOrder);

        assertFalse(deleteResult);
    }

    @Test
    void getOrdersByCustomerIdReturnsList() {
        Order testOrder = factory.generateTestOrder(customer);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(entityManagerMock.createQuery("select order from Order order where order.customerId = :customerId", Order.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepository.getOrdersByCustomerId(customer.getCustomerId());

        assertFalse(all.isEmpty());
    }

    @Test
    void getOrdersByCustomerIdProcessException() {
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.createQuery("select order from Order order where order.customerId = :customerId", Order.class)).thenThrow(IllegalArgumentException.class);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepository.getOrdersByCustomerId(1);

        assertTrue(all.isEmpty());
    }

    @Test
    void getAllReturnsList() {
        Order testOrder = factory.generateTestOrder(customer);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(entityManagerMock.createQuery("select order from Order order", Order.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepository.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Order testOrder = factory.generateTestOrder(customer);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(entityManagerMock.createQuery("select order from Order order", Order.class)).thenThrow(IllegalArgumentException.class);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepository.getAll();

        assertTrue(all.isEmpty());
    }
}