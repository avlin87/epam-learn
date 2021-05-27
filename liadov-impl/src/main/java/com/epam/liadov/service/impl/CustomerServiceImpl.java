package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

/**
 * CustomerServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");
    private final CustomerRepository customerRepository = new CustomerRepository(entityPU);

    @Override
    public Customer save(Customer customer) {
        Customer createdCustomer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.save(customer);
        if (optionalCustomer.isPresent()) {
            createdCustomer = optionalCustomer.get();
            log.trace("Customer created successfully");
        } else {
            log.trace("Customer was not created");
        }
        return createdCustomer;
    }

    @Override
    public boolean update(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public Customer find(int primaryKey) {
        return customerRepository.find(primaryKey).orElse(new Customer());
    }

    @Override
    public boolean delete(Customer customer) {
        return customerRepository.delete(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.getAll();
    }
}
