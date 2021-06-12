package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.CustomerDtoToCustomerConverter;
import com.epam.liadov.converter.CustomerToCustomerDtoConverter;
import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.dto.CustomerDto;
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
    @Mock
    private CustomerToCustomerDtoConverter customerToCustomerDtoConverter;
    @Mock
    private CustomerDtoToCustomerConverter customerDtoToCustomerConverter;

    private EntityFactory factory;
    private CustomerToCustomerDtoConverter toCustomerDtoConverter = new CustomerToCustomerDtoConverter();

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
        CustomerDto testCustomerDto = toCustomerDtoConverter.convert(testCustomer);
        when(customerService.find(anyInt())).thenReturn(testCustomer);
        when(customerToCustomerDtoConverter.convert(any())).thenReturn(testCustomerDto);

        CustomerDto customerDto = customerResource.getCustomer(1);

        assertEquals(testCustomerDto, customerDto);
    }

    @Test
    void addCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerDto testCustomerDto = toCustomerDtoConverter.convert(testCustomer);
        when(customerToCustomerDtoConverter.convert(any())).thenReturn(testCustomerDto);
        when(customerDtoToCustomerConverter.convert(any())).thenReturn(testCustomer);
        when(customerService.save(any())).thenReturn(testCustomer);

        CustomerDto customerDto = customerResource.addCustomer(testCustomerDto);

        assertEquals(testCustomerDto, customerDto);
    }

    @Test
    void deleteCustomer() {
        when(customerService.delete(anyInt())).thenReturn(true);

        customerResource.deleteCustomer(1);
    }

    @Test
    void updateCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        CustomerDto testCustomerDto = toCustomerDtoConverter.convert(testCustomer);
        when(customerService.update(any())).thenReturn(testCustomer);
        when(customerToCustomerDtoConverter.convert(any())).thenReturn(testCustomerDto);

        CustomerDto customerDto = customerResource.updateCustomer(testCustomerDto);

        assertEquals(testCustomerDto, customerDto);
    }

    @Test
    void getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        when(customerService.getAll()).thenReturn(customers);

        List<CustomerDto> customerList = customerResource.getAllCustomers();

        assertNotNull(customerList);
    }
}