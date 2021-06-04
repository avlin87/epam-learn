package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public boolean save(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.save(customer);
        boolean saveResult = optionalCustomer.isPresent();
        log.trace("Customer created: {}", saveResult);
        return saveResult;
    }

    @Override
    public boolean update(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.update(customer);
        return optionalCustomer.isPresent();
    }

    @Override
    public Customer find(int primaryKey) {
        Optional<Customer> optionalCustomer = customerRepository.find(primaryKey);
        return optionalCustomer.orElse(new Customer());
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
