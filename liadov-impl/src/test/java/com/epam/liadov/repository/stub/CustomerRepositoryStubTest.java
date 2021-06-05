package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
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
 * CustomerRepositoryStubTest - test for {@link CustomerRepositoryStub}
 *
 * @author Aleksandr Liadov
 */
class CustomerRepositoryStubTest {
    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private CustomerRepositoryStub customerRepository;

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

        Optional<Customer> optionalCustomer = customerRepository.save(customer);

        boolean saveResult = optionalCustomer.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnOptionalWithCustomer() {
        Customer customer = factory.generateTestCustomer();

        Optional<Customer> optionalCustomer = customerRepository.update(customer);

        boolean updateResult = optionalCustomer.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Customer> optionalCustomer = customerRepository.find(0);

        assertTrue(optionalCustomer.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Customer customer = factory.generateTestCustomer();
        boolean deleteResult = customerRepository.delete(customer);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Customer> all = customerRepository.getAll();

        assertFalse(all.isEmpty());
    }
}