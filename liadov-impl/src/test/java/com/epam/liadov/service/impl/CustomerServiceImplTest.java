package com.epam.liadov.service.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.repository.stub.SupplierRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void saveReturnTrue() {
        Customer customer = factory.generateTestCustomer();
        when(customerRepository.save(any())).thenReturn(Optional.ofNullable(customer));

        boolean saveResult = customerServiceImpl.save(customer);

        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Customer customer = factory.generateTestCustomer();
        when(customerRepository.update(any())).thenReturn(Optional.ofNullable(customer));

        boolean saveResult = customerServiceImpl.update(customer);

        assertTrue(saveResult);
    }

    @Test
    void findReturnNotNull() {
        Customer expectedValue = factory.generateTestCustomer();
        when(customerRepository.find(anyInt())).thenReturn(Optional.ofNullable(expectedValue));

        Customer customer = customerServiceImpl.find(1);

        assertNotNull(customer);
    }

    @Test
    void delete() {
        Customer customer = factory.generateTestCustomer();
        when(customerRepository.delete(any())).thenReturn(true);

        boolean deleteResult = customerServiceImpl.delete(customer);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnList() {
        List<Customer> all = customerServiceImpl.getAll();

        assertNotNull(all);
    }
}