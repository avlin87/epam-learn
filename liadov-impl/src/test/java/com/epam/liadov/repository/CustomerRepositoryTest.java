package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * CustomerRepositoryTest test for {@link CustomerRepository}
 *
 * @author Aleksandr Liadov
 */

class CustomerRepositoryTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Customer> typedQuery;

    @InjectMocks
    private CustomerRepository customerRepository;

    private EntityFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnsTrue() {
        Customer testCustomer = factory.generateTestCustomer();

        boolean saveResult = customerRepository.save(testCustomer);

        assertTrue(saveResult);
    }

    @Test
    void saveReturnsFalse() {
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        boolean save = customerRepository.save(null);

        assertFalse(save);
    }

    @Test
    void updateReturnThue() {
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.find(Customer.class, testCustomer.getCustomerId())).thenReturn(testCustomer);

        boolean customerUpdated = customerRepository.update(testCustomer);

        assertTrue(customerUpdated);
    }

    @Test
    void updateReturnFalse() {
        Customer testCustomer = factory.generateTestCustomer();
        when(entityManagerMock.merge(testCustomer)).thenThrow(TransactionRequiredException.class);

        boolean customerUpdated = customerRepository.update(testCustomer);

        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullCustomer() {
        Executable executable = () -> customerRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Customer> optionalCustomer = customerRepository.find(0);

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Customer.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Customer> optionalCustomer = customerRepository.find(1);

        assertFalse(optionalCustomer.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Customer testCustomer = factory.generateTestCustomer();

        boolean deleteResult = customerRepository.delete(testCustomer);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        Customer testCustomer = factory.generateTestCustomer();
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = customerRepository.delete(testCustomer);

        assertFalse(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Customer testCustomer = factory.generateTestCustomer();
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
        when(entityManagerMock.createQuery("select customer from Customer customer", Customer.class)).thenThrow(IllegalArgumentException.class);
        List<Customer> list = new ArrayList<>();
        list.add(testCustomer);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Customer> all = customerRepository.getAll();

        assertTrue(all.isEmpty());
    }
}