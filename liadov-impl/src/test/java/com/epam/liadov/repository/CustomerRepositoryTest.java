package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
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
 * CustomerRepositoryTest test for {@link CustomerRepository}
 *
 * @author Aleksandr Liadov
 */
class CustomerRepositoryTest {

    private EntityFactory factory;
    private EntityManagerFactory entityManagerFactoryMock;
    private EntityManager entityManagerMock;
    private EntityTransaction transactionMock;
    private Query query;
    private TypedQuery<Customer> typedQuery;

    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);
        typedQuery = (TypedQuery<Customer>) mock(QueryImpl.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);

        when(this.query.setParameter(anyInt(), anyInt())).thenReturn(this.query);
        when(this.query.executeUpdate()).thenReturn(1);


        factory = new EntityFactory();
    }

    @Test
    void saveReturnsCustomerObject() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        Optional<Customer> customerOptional = customerRepository.save(testCustomer);

        assertTrue(customerOptional.get() instanceof Customer);
    }

    @Test
    void saveReturnsEmptyObject() {
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        Optional<Customer> customerOptional = customerRepository.save(null);

        assertFalse(customerOptional.isPresent());
    }

    @Test
    void saveProcessException() {
        doThrow(IllegalArgumentException.class).when(entityManagerMock).persist(null);
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        Optional<Customer> customerOptional = customerRepository.save(null);

        assertFalse(customerOptional.isPresent());
    }

    @Test
    void updateReturnThue() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);
        when(entityManagerMock.find(Customer.class, testCustomer.getCustomerId())).thenReturn(testCustomer);

        boolean customerUpdated = customerRepository.update(testCustomer);

        assertTrue(customerUpdated);
    }

    @Test
    void updateReturnFalse() {
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.merge(testCustomer)).thenThrow(TransactionRequiredException.class);
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        boolean customerUpdated = customerRepository.update(testCustomer);

        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullCustomer() {
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        Executable executable = () -> customerRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        Optional<Customer> optionalCustomer = customerRepository.find(0);

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Customer.class, 1)).thenThrow(IllegalArgumentException.class);
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        Optional<Customer> optionalCustomer = customerRepository.find(1);

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);

        boolean deleteResult = customerRepository.delete(testCustomer);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select customer from Customer customer", Customer.class)).thenReturn(typedQuery);
        List<Customer> list = new ArrayList<>();
        list.add(testCustomer);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Customer> all = customerRepository.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerRepository customerRepository = new CustomerRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select customer from Customer customer", Customer.class)).thenThrow(IllegalArgumentException.class);
        List<Customer> list = new ArrayList<>();
        list.add(testCustomer);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Customer> all = customerRepository.getAll();

        assertTrue(all.isEmpty());
    }
}