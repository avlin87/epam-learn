package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * CustomerServiceImplTest - test for {@link CustomerServiceImpl}
 *
 * @author Aleksandr Liadov
 */
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;
    private EntityFactory factory;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnCustomer() {
        Customer customer = factory.generateTestCustomer();
        when(customerRepository.save(any())).thenReturn(customer);

        Customer saveResult = customerServiceImpl.save(customer);

        assertEquals(customer, saveResult);
    }

    @Test
    void updateReturnCustomer() {
        Customer customer = factory.generateTestCustomer();
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.save(any())).thenReturn(customer);

        Customer updateResult = customerServiceImpl.update(customer);

        assertEquals(customer, updateResult);
    }

    @Test
    void findReturnNotNull() {
        Customer expectedValue = factory.generateTestCustomer();
        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(expectedValue));

        Customer customer = customerServiceImpl.find(1);

        assertNotNull(customer);
    }

    @Test
    void delete() {
        Customer customer = factory.generateTestCustomer();
        int customerId = customer.getCustomerId();
        when(customerRepository.existsById(anyInt())).thenReturn(true).thenReturn(false);

        boolean deleteResult = customerServiceImpl.delete(customerId);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnList() {
        List<Customer> all = customerServiceImpl.getAll();

        assertNotNull(all);
    }
}