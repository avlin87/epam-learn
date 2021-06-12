package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.CustomerDtoToCustomerConverter;
import com.epam.liadov.converter.CustomerToCustomerDtoConverter;
import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.dto.CustomerDto;
import com.epam.liadov.resource.CustomerResource;
import com.epam.liadov.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CustomerResourceImpl - class for RestController implementation of CustomerResource interface
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerResourceImpl implements CustomerResource {

    private final CustomerService customerService;
    private final CustomerToCustomerDtoConverter toCustomerDtoConverter;
    private final CustomerDtoToCustomerConverter toCustomerConverter;

    @Override
    public CustomerDto getCustomer(Integer id) {
        Customer customer = customerService.find(id);
        log.debug("found customer: {}", customer);
        CustomerDto customerDto = toCustomerDtoConverter.convert(customer);
        return customerDto;
    }

    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = toCustomerConverter.convert(customerDto);
        Customer savedCustomer = customerService.save(customer);
        log.debug("created customer: {}", savedCustomer);
        customerDto = toCustomerDtoConverter.convert(savedCustomer);
        return customerDto;
    }

    @Override
    public void deleteCustomer(Integer id) {
        boolean isDeleted = customerService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer customer = toCustomerConverter.convert(customerDto);
        Customer updatedCustomer = customerService.update(customer);
        log.debug("updated customer: {}", updatedCustomer);
        customerDto = toCustomerDtoConverter.convert(updatedCustomer);
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerService.getAll();
        log.trace("Get all customers: {}", customerList);
        List<CustomerDto> customerDtoList = customerList.stream()
                .map(toCustomerDtoConverter::convert)
                .collect(Collectors.toList());
        return customerDtoList;
    }
}