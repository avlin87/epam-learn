package com.epam.liadov.repository.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * OrderProductRepositoryImplImplTest test for {@link OrderProductRepositoryImpl}
 *
 * @author Aleksandr Liadov
 */
class OrderProductRepositoryImplImplTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private Query query;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private OrderProductRepositoryImpl orderProductRepositoryImpl;

    private EntityFactory factory;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();

        when(entityManagerMock.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyInt(), anyInt())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
    }

    @Test
    void saveIdExecutesSuccessfully() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));

        boolean saveExecutedResult = orderProductRepositoryImpl.saveId(order.getOrderID(), order.getProductId());

        assertTrue(saveExecutedResult);
    }

    @Test
    void saveIdProcessException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));
        when(query.executeUpdate()).thenThrow(TransactionRequiredException.class);

        boolean saveExecutedResult = orderProductRepositoryImpl.saveId(order.getOrderID(), order.getProductId());

        assertFalse(saveExecutedResult);
    }

    @Test
    void updateExecutesSuccessfully() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));

        boolean saveExecutedResult = orderProductRepositoryImpl.updateId(order.getOrderID(), order.getProductId());

        assertTrue(saveExecutedResult);
    }

    @Test
    void updateProcessException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));
        when(query.executeUpdate()).thenThrow(TransactionRequiredException.class);

        boolean saveExecutedResult = orderProductRepositoryImpl.updateId(order.getOrderID(), order.getProductId());

        assertFalse(saveExecutedResult);
    }

}