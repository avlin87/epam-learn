package com.epam.liadov.repository.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * CustomerRepositoryImplTest test for {@link CustomerRepositoryImpl}
 *
 * @author Aleksandr Liadov
 */

class CustomerRepositoryImplTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Customer> typedQuery;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private CustomerRepositoryImpl customerRepositoryImpl;

    private EntityFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnsOptionalWithCustomer() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer testCustomer = factory.generateTestCustomer();

        Optional<Customer> optionalCustomer = customerRepositoryImpl.save(testCustomer);

        boolean saveResult = optionalCustomer.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void saveReturnsEmptyOptional() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        Optional<Customer> optionalCustomer = customerRepositoryImpl.save(null);

        boolean save = optionalCustomer.isPresent();
        assertFalse(save);
    }

    @Test
    void updateReturnOptionalWithCustomer() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.find(Customer.class, testCustomer.getCustomerId())).thenReturn(testCustomer);
        when(entityManagerMock.merge(any())).thenReturn(testCustomer);

        Optional<Customer> optionalCustomer = customerRepositoryImpl.update(testCustomer);

        boolean customerUpdated = optionalCustomer.isPresent();
        assertTrue(customerUpdated);
    }

    @Test
    void updateReturnEmptyOptional() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.merge(testCustomer)).thenThrow(TransactionRequiredException.class);

        Optional<Customer> optionalCustomer = customerRepositoryImpl.update(testCustomer);

        boolean customerUpdated = optionalCustomer.isPresent();
        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullCustomer() {
        Executable executable = () -> customerRepositoryImpl.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Customer> optionalCustomer = customerRepositoryImpl.find(0);

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Customer.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Customer> optionalCustomer = customerRepositoryImpl.find(1);

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer testCustomer = factory.generateTestCustomer();

        boolean deleteResult = customerRepositoryImpl.delete(testCustomer);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Customer testCustomer = factory.generateTestCustomer();
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = customerRepositoryImpl.delete(testCustomer);

        assertFalse(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.createQuery("select customer from Customer customer", Customer.class)).thenReturn(typedQuery);
        List<Customer> list = new ArrayList<>();
        list.add(testCustomer);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Customer> all = customerRepositoryImpl.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.createQuery("select customer from Customer customer", Customer.class)).thenThrow(IllegalArgumentException.class);
        List<Customer> list = new ArrayList<>();
        list.add(testCustomer);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Customer> all = customerRepositoryImpl.getAll();

        assertTrue(all.isEmpty());
    }
}