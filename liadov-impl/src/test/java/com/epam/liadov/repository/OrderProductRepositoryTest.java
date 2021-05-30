package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * OrderProductRepositoryTest test for {@link OrderProductRepository}
 *
 * @author Aleksandr Liadov
 */
class OrderProductRepositoryTest {

    private EntityFactory factory;
    private EntityManagerFactory entityManagerFactoryMock;
    private EntityManager entityManagerMock;
    private EntityTransaction transactionMock;
    private Query query;


    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);
        when(entityManagerMock.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyInt(), anyInt())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        factory = new EntityFactory();
    }

    @Test
    void saveExecutesSuccessfully() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        OrderProductRepository orderProductRepository = new OrderProductRepository(entityManagerFactoryMock);

        boolean saveExecutedResult = orderProductRepository.save(order, product);

        assertTrue(saveExecutedResult);
    }

    @Test
    void saveProcessException() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        OrderProductRepository orderProductRepository = new OrderProductRepository(entityManagerFactoryMock);
        when(query.executeUpdate()).thenThrow(TransactionRequiredException.class);

        boolean saveExecutedResult = orderProductRepository.save(order, product);

        assertFalse(saveExecutedResult);
    }

    @Test
    void saveIdExecutesSuccessfully() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1,2));
        OrderProductRepository orderProductRepository = new OrderProductRepository(entityManagerFactoryMock);

        boolean saveExecutedResult = orderProductRepository.saveId(order.getOrderID(), order.getProductId());

        assertTrue(saveExecutedResult);
    }

    @Test
    void saveIdProcessException() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1,2));
        OrderProductRepository orderProductRepository = new OrderProductRepository(entityManagerFactoryMock);
        when(query.executeUpdate()).thenThrow(TransactionRequiredException.class);

        boolean saveExecutedResult = orderProductRepository.saveId(order.getOrderID(), order.getProductId());

        assertFalse(saveExecutedResult);
    }
}