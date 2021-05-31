package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * OrderProductRepositoryTest test for {@link OrderProductRepository}
 *
 * @author Aleksandr Liadov
 */
class OrderProductRepositoryTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private Query query;

    @InjectMocks
    private OrderProductRepository orderProductRepository;

    private EntityFactory factory;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();

        when(entityManagerMock.createNativeQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyInt(), anyInt())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
    }



    /*@BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);

        when(query.executeUpdate()).thenReturn(1);

        factory = new EntityFactory();
    }*/

    @Test
    void saveIdExecutesSuccessfully() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));

        boolean saveExecutedResult = orderProductRepository.saveId(order.getOrderID(), order.getProductId());

        assertTrue(saveExecutedResult);
    }

    @Test
    void saveIdProcessException() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));
        when(query.executeUpdate()).thenThrow(TransactionRequiredException.class);

        boolean saveExecutedResult = orderProductRepository.saveId(order.getOrderID(), order.getProductId());

        assertFalse(saveExecutedResult);
    }

    @Test
    void updateExecutesSuccessfully() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));

        boolean saveExecutedResult = orderProductRepository.updateId(order.getOrderID(), order.getProductId());

        assertTrue(saveExecutedResult);
    }

    @Test
    void updateProcessException() {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setProductId(List.of(1, 2));
        when(query.executeUpdate()).thenThrow(TransactionRequiredException.class);

        boolean saveExecutedResult = orderProductRepository.updateId(order.getOrderID(), order.getProductId());

        assertFalse(saveExecutedResult);
    }

}