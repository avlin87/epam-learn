package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.Customer;
import com.epam.liadov.domain.factory.EntityFactory;
import com.epam.liadov.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * CustomerResourceImplTest - test for {@link CustomerResourceImpl}
 *
 * @author Aleksandr Liadov
 */
class CustomerResourceImplTest {

    @Mock
    private CustomerServiceImpl customerService;
    private EntityFactory factory;

    @InjectMocks
    private CustomerResourceImpl customerResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void getCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.find(anyInt())).thenReturn(testCustomer);

        Customer customer = customerResource.getCustomer(1);

        assertEquals(testCustomer, customer);
    }

    @Test
    void addCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.save(any())).thenReturn(testCustomer);

        Customer customer = customerResource.addCustomer(testCustomer);

        assertEquals(testCustomer, customer);
    }

    @Test
    void deleteCustomer() {
        when(customerService.delete(anyInt())).thenReturn(true);

        customerResource.deleteCustomer(1);
    }

    @Test
    void updateCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.update(any())).thenReturn(testCustomer);

        Customer customer = customerResource.updateCustomer(testCustomer);

        assertEquals(testCustomer, customer);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(customerService.getAll()).thenReturn(customers);

        List<Customer> customerList = customerResource.getAllCustomers();

        assertNotNull(customerList);
    }
}