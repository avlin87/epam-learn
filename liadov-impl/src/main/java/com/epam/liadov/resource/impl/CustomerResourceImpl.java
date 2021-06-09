package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.Customer;
import com.epam.liadov.resource.CustomerResource;
import com.epam.liadov.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CustomerResourceImpl - class for RestController implementation of CustomerResource interface
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerResourceImpl implements CustomerResource<Customer> {

    private final CustomerService customerService;

    @Override
    public Customer getCustomer(Integer id) {
        Customer customer = customerService.find(id);
        log.debug("found customer: {}", customer);
        return customer;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Customer savedCustomer = customerService.save(customer);
        log.debug("created customer: {}", savedCustomer);
        return savedCustomer;
    }

    @Override
    public void deleteCustomer(Integer id) {
        boolean isDeleted = customerService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer updatedCustomer = customerService.update(customer);
        log.debug("updated customer: {}", updatedCustomer);
        return updatedCustomer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = customerService.getAll();
        log.trace("Get all customers: {}", customerList);
        return customerList;
    }
}