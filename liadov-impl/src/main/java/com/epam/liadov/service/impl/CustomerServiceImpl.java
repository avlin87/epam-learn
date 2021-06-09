package com.epam.liadov.service.impl;

import com.epam.liadov.domain.Customer;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CustomerServiceImpl - Service for operations with Customer repository
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!local")
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.save(customer));
        boolean saveResult = optionalCustomer.isPresent();
        log.trace("Customer created: {}", saveResult);
        if (saveResult) {
            customer = optionalCustomer.get();
            return customer;
        }
        return new Customer();
    }

    @Override
    public Customer update(Customer customer) {
        int customerId = customer.getCustomerId();
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            optionalCustomer = Optional.ofNullable(customerRepository.save(customer));
        }
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
            log.trace("Customer updated: {}", customer);
            return customer;
        }
        log.trace("Customer was not updated");
        return new Customer();
    }

    @Override
    public Customer find(int primaryKey) {
        Optional<Customer> optionalCustomer = customerRepository.findById(primaryKey);
        boolean findResult = optionalCustomer.isPresent();
        log.trace("Customer found: {}", findResult);
        if (findResult) {
            Customer customer = optionalCustomer.get();
            return customer;
        }
        return new Customer();
    }

    @Override
    public boolean delete(int primaryKey) {
        boolean existsInDb = customerRepository.existsById(primaryKey);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            customerRepository.deleteById(primaryKey);
            boolean entityExistAfterRemove = customerRepository.existsById(primaryKey);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        return false;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}