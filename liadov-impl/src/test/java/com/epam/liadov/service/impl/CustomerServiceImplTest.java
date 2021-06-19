package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.exception.NotFoundException;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CustomerServiceImplTest - test for {@link CustomerServiceImpl}
 *
 * @author Aleksandr Liadov
 */
@DataJpaTest
@RunWith(SpringRunner.class)
class CustomerServiceImplTest {

    private final EntityFactory factory = new EntityFactory();

    @Autowired
    private CustomerService customerService;

    @Test
    void saveReturnCustomer() {
        Customer testCustomer = factory.generateTestCustomer();

        Customer saveResult = customerService.save(testCustomer);

        assertEquals(testCustomer, saveResult);
    }

    @Test
    void updateReturnCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        testCustomer = customerService.save(testCustomer);
        testCustomer.setCustomerName("Updated Customer")
                .setPhone("22222");

        Customer updateResult = customerService.update(testCustomer);

        assertEquals(testCustomer, updateResult);
    }

    @Test
    void updateThrowNotFoundException() {
        Customer testCustomer = factory.generateTestCustomer();
        testCustomer = customerService.save(testCustomer);
        int testCustomerId = testCustomer.getCustomerId() + 999;
        testCustomer.setCustomerId(testCustomerId);
        Customer finalTestCustomer = testCustomer;

        Executable executeUpdate = () -> customerService.update(finalTestCustomer);

        assertThrows(NotFoundException.class, executeUpdate);
    }

    @Test
    void findReturnCustomer() {
        Customer testCustomer = factory.generateTestCustomer();
        testCustomer = customerService.save(testCustomer);
        int testCustomerId = testCustomer.getCustomerId();

        Customer foundCustomer = customerService.find(testCustomerId);

        assertEquals(testCustomer, foundCustomer);
    }

    @Test
    void findThrowNotFoundException() {
        Customer testCustomer = factory.generateTestCustomer();
        testCustomer = customerService.save(testCustomer);
        int testCustomerId = testCustomer.getCustomerId() + 999;

        Executable executeUpdate = () -> customerService.find(testCustomerId);

        assertThrows(NotFoundException.class, executeUpdate);
    }

    @Test
    void deleteReturnTrue() {
        Customer testCustomer = factory.generateTestCustomer();
        testCustomer = customerService.save(testCustomer);
        int testCustomerId = testCustomer.getCustomerId();

        boolean deleteResult = customerService.delete(testCustomerId);

        assertTrue(deleteResult);
    }

    @Test
    void deleteThrowNotFoundException() {
        Customer testCustomer = factory.generateTestCustomer();
        testCustomer = customerService.save(testCustomer);
        int testCustomerId = testCustomer.getCustomerId();

        Executable executeDelete = () -> customerService.delete(testCustomerId + 999);

        assertThrows(NotFoundException.class, executeDelete);
    }

    @Test
    void getAllReturnList() {
        List<Customer> all = customerService.getAll();

        assertNotNull(all);
    }

    @TestConfiguration
    static class MyTestConfiguration {
        @Bean
        public CustomerService customerService(CustomerRepository repository) {
            return new CustomerServiceImpl(repository);
        }
    }
}