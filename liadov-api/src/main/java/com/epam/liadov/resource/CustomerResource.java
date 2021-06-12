package com.epam.liadov.resource;

import com.epam.liadov.dto.CustomerDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CustomerResource - interface for Rest operations with Customer entity
 *
 * @author Aleksandr Liadov
 */
@RequestMapping("/customer")
public interface CustomerResource {

    /**
     * Method for requesting Customer by id
     *
     * @param id - id of requested entity
     * @return entity object
     */
    @GetMapping("/{id}")
    CustomerDto getCustomer(@PathVariable Integer id);

    /**
     * Method parse json POST as Customer object
     *
     * @param customerDto entity object
     * @return entity object
     */
    @PostMapping
    CustomerDto addCustomer(@RequestBody CustomerDto customerDto);

    /**
     * Method parse json PUT as Customer object
     *
     * @param customerDto entity object
     * @return entity object
     */
    @PutMapping
    CustomerDto updateCustomer(@RequestBody CustomerDto customerDto);

    /**
     * Method for delete request of Customer entity
     *
     * @param id id of entity to be removed
     * @return true if entity removed
     */
    @DeleteMapping("/{id}")
    void deleteCustomer(@PathVariable Integer id);

    /**
     * Method for requesting all of Customer entities
     *
     * @return List of entities
     */
    @GetMapping
    List<CustomerDto> getAllCustomers();
}