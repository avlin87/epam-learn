package com.epam.liadov.service.stub;

import com.epam.liadov.domain.Customer;
import com.epam.liadov.domain.factory.EntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * CustomerServiceStubTest - test for {@link CustomerServiceStub}
 *
 * @author Aleksandr Liadov
 */
class CustomerServiceStubTest {
    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private CustomerServiceStub customerServiceStub;

    private EntityFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        when(factoryTest.generateTestCustomer()).thenReturn(factory.generateTestCustomer());
    }

    @Test
    void saveReturnOptionalWithCustomer() {
        Customer customer = factory.generateTestCustomer();

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerServiceStub.save(customer));

        boolean saveResult = optionalCustomer.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnOptionalWithCustomer() {
        Customer customer = factory.generateTestCustomer();

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerServiceStub.update(customer));

        boolean updateResult = optionalCustomer.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerServiceStub.find(0));

        assertTrue(optionalCustomer.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Customer customer = factory.generateTestCustomer();
        int customerId = customer.getCustomerId();

        boolean deleteResult = customerServiceStub.delete(customerId);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Customer> all = customerServiceStub.getAll();

        assertFalse(all.isEmpty());
    }
}