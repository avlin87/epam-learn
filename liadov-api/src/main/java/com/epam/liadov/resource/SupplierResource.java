package com.epam.liadov.resource;

import com.epam.liadov.dto.SupplierDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SupplierResource - interface for Rest operations with Supplier entity
 *
 * @author Aleksandr Liadov
 */
@RequestMapping("/supplier")
public interface SupplierResource {

    /**
     * Method for requesting Supplier by id
     *
     * @param id - id of requested entity
     * @return entity object
     */
    @GetMapping("/{id}")
    SupplierDto getSupplier(@PathVariable Integer id);

    /**
     * Method parse json POST as Supplier object
     *
     * @param supplierDto entity object
     * @return entity object
     */
    @PostMapping
    SupplierDto addSupplier(@RequestBody SupplierDto supplierDto);

    /**
     * Method parse json PUT as Supplier object
     *
     * @param supplierDto entity object
     * @return entity object
     */
    @PutMapping
    SupplierDto updateSupplier(@RequestBody SupplierDto supplierDto);

    /**
     * Method for delete request of Supplier entity
     *
     * @param id id of entity to be removed
     * @return true if entity removed
     */
    @DeleteMapping("/{id}")
    void deleteSupplier(@PathVariable Integer id);

    /**
     * Method for requesting all of Supplier entities
     *
     * @return List of entities
     */
    @GetMapping
    List<SupplierDto> getAllSuppliers();
}