package com.epam.liadov.resource;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CustomerResource - interface for Rest operations with Customer entity
 *
 * @param <T> entity
 * @author Aleksandr Liadov
 */
@RequestMapping("/customer")
public interface CustomerResource<T> {

    /**
     * Method for requesting Customer by id
     *
     * @param id - id of requested entity
     * @return entity object
     */
    @GetMapping(params = "id")
    T getCustomer(@RequestParam Integer id);

    /**
     * Method parse json POST as Customer object
     *
     * @param customer entity object
     * @return entity object
     */
    @PostMapping
    T addCustomer(@RequestBody T customer);

    /**
     * Method parse json PUT as Customer object
     *
     * @param customer entity object
     * @return entity object
     */
    @PutMapping
    T updateCustomer(@RequestBody T customer);

    /**
     * Method for delete request of Customer entity
     *
     * @param id id of entity to be removed
     * @return true if entity removed
     */
    @DeleteMapping(params = "id")
    void deleteCustomer(@RequestParam Integer id);

    /**
     * Method for requesting all of Customer entities
     *
     * @return List of entities
     */
    @GetMapping
    List<T> getAllCustomers();
}