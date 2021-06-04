package com.epam.liadov.repository.impl;

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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * OrderRepositoryImplTest test for {@link OrderRepositoryImpl}
 *
 * @author Aleksandr Liadov
 */
class OrderRepositoryImplTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Order> typedQuery;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private OrderRepositoryImpl orderRepositoryImpl;

    private EntityFactory factory;
    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        customer = factory.generateTestCustomer();
    }

    @Test
    void saveReturnsTrueOptionalWithOrder() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Order testOrder = factory.generateTestOrder(customer);

        Optional<Order> optionalOrder = orderRepositoryImpl.save(testOrder);

        boolean saveResult = optionalOrder.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void saveReturnsEmptyOptional() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        Optional<Order> optionalOrder = orderRepositoryImpl.save(null);

        boolean saveResult = optionalOrder.isPresent();
        assertFalse(saveResult);
    }

    @Test
    void updateReturnOptionalWithOrder() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.find(Order.class, testOrder.getOrderID())).thenReturn(testOrder);
        when(entityManagerMock.merge(any())).thenReturn(testOrder);

        Optional<Order> optionalOrder = orderRepositoryImpl.update(testOrder);

        boolean orderUpdated = optionalOrder.isPresent();
        assertTrue(orderUpdated);
    }

    @Test
    void updateReturnEmptyOptional() {
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.merge(testOrder)).thenThrow(TransactionRequiredException.class);

        Optional<Order> optionalOrder = orderRepositoryImpl.update(testOrder);

        boolean customerUpdated = optionalOrder.isPresent();
        assertFalse(customerUpdated);
    }

    @Test
    void updateReturnEmptyOptionalDueToException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.find(Order.class, testOrder.getOrderID())).thenReturn(testOrder);
        when(entityManagerMock.merge(testOrder)).thenThrow(TransactionRequiredException.class);

        Optional<Order> optionalOrder = orderRepositoryImpl.update(testOrder);

        boolean customerUpdated = optionalOrder.isPresent();
        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullOrder() {
        Executable executable = () -> orderRepositoryImpl.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Order> optionalOrder = orderRepositoryImpl.find(0);

        assertFalse(optionalOrder.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Order.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Order> optionalOrder = orderRepositoryImpl.find(1);

        assertFalse(optionalOrder.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Order testOrder = factory.generateTestOrder(customer);

        boolean deleteResult = orderRepositoryImpl.delete(testOrder);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Order testOrder = factory.generateTestOrder(customer);
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = orderRepositoryImpl.delete(testOrder);

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

        List<Order> all = orderRepositoryImpl.getOrdersByCustomerId(customer.getCustomerId());

        assertFalse(all.isEmpty());
    }

    @Test
    void getOrdersByCustomerIdProcessException() {
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.createQuery("select order from Order order where order.customerId = :customerId", Order.class)).thenThrow(IllegalArgumentException.class);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepositoryImpl.getOrdersByCustomerId(1);

        assertTrue(all.isEmpty());
    }

    @Test
    void getAllReturnsList() {
        Order testOrder = factory.generateTestOrder(customer);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(entityManagerMock.createQuery("select order from Order order", Order.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepositoryImpl.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Order testOrder = factory.generateTestOrder(customer);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(entityManagerMock.createQuery("select order from Order order", Order.class)).thenThrow(IllegalArgumentException.class);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepositoryImpl.getAll();

        assertTrue(all.isEmpty());
    }
}