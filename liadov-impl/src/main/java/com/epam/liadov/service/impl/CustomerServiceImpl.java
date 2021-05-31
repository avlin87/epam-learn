package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * CustomerServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean save(Customer customer) {
        boolean saveResult = customerRepository.save(customer);
        log.trace("Customer created: {}", saveResult);
        return saveResult;
    }

    @Override
    public boolean update(Customer customer) {
        return customerRepository.update(customer);
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
