package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.persistence.*;
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

    private EntityFactory factory;
    private EntityManagerFactory entityManagerFactoryMock;
    private EntityManager entityManagerMock;
    private EntityTransaction transactionMock;
    private Query query;
    private TypedQuery<Order> typedQuery;

    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);
        typedQuery = (TypedQuery<Order>) mock(QueryImpl.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);

        factory = new EntityFactory();
    }

    @Test
    void saveReturnsOrderObject() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        Optional<Order> orderOptional = orderRepository.save(testOrder);

        assertTrue(orderOptional.get() instanceof Order);
    }

    @Test
    void saveReturnsEmptyObject() {
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        Optional<Order> orderOptional = orderRepository.save(null);

        assertFalse(orderOptional.isPresent());
    }

    @Test
    void saveProcessException() {
        doThrow(IllegalArgumentException.class).when(entityManagerMock).persist(null);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        Optional<Order> orderOptional = orderRepository.save(null);

        assertFalse(orderOptional.isPresent());
    }

    @Test
    void updateReturnThue() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);
        when(entityManagerMock.find(Order.class, testOrder.getOrderID())).thenReturn(testOrder);

        boolean orderUpdated = orderRepository.update(testOrder);

        assertTrue(orderUpdated);
    }

    @Test
    void updateReturnFalse() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(entityManagerMock.merge(testOrder)).thenThrow(TransactionRequiredException.class);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        boolean customerUpdated = orderRepository.update(testOrder);

        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullOrder() {
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        Executable executable = () -> orderRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        Optional<Order> optionalOrder = orderRepository.find(0);

        assertFalse(optionalOrder.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Order.class, 1)).thenThrow(IllegalArgumentException.class);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        Optional<Order> optionalOrder = orderRepository.find(1);

        assertFalse(optionalOrder.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);

        boolean deleteResult = orderRepository.delete(testOrder);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select order from Order order", Order.class)).thenReturn(typedQuery);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepository.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        OrderRepository orderRepository = new OrderRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select order from Order order", Order.class)).thenThrow(IllegalArgumentException.class);
        List<Order> list = new ArrayList<>();
        list.add(testOrder);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Order> all = orderRepository.getAll();

        assertTrue(all.isEmpty());
    }
}