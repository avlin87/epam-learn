package com.epam.liadov.resource;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SupplierResource - interface for Rest operations with Supplier entity
 *
 * @param <T> entity
 * @author Aleksandr Liadov
 */
@RequestMapping("/supplier")
public interface SupplierResource<T> {

    /**
     * Method for requesting Supplier by id
     *
     * @param id - id of requested entity
     * @return entity object
     */
    @GetMapping(params = "id")
    T getSupplier(@RequestParam Integer id);

    /**
     * Method parse json POST as Supplier object
     *
     * @param supplier entity object
     * @return entity object
     */
    @PostMapping
    T addSupplier(@RequestBody T supplier);

    /**
     * Method parse json PUT as Supplier object
     *
     * @param supplier entity object
     * @return entity object
     */
    @PutMapping
    T updateSupplier(@RequestBody T supplier);

    /**
     * Method for delete request of Supplier entity
     *
     * @param id id of entity to be removed
     * @return true if entity removed
     */
    @DeleteMapping(params = "id")
    void deleteSupplier(@RequestParam Integer id);

    /**
     * Method for requesting all of Supplier entities
     *
     * @return List of entities
     */
    @GetMapping
    List<T> getAllSuppliers();
}