package com.epam.liadov.resource;

import com.epam.liadov.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductResource - interface for Rest operations with Product entity
 *
 * @author Aleksandr Liadov
 */
@RequestMapping("/product")
public interface ProductResource {

    /**
     * Method for requesting Product by id
     *
     * @param id - id of requested entity
     * @return entity object
     */
    @GetMapping(params = "id")
    ProductDto getProduct(@RequestParam Integer id);

    /**
     * Method parse json POST as Product object
     *
     * @param product entity object
     * @return entity object
     */
    @PostMapping
    ProductDto addProduct(@RequestBody ProductDto product);

    /**
     * Method parse json PUT as Product object
     *
     * @param product entity object
     * @return entity object
     */
    @PutMapping
    ProductDto updateProduct(@RequestBody ProductDto product);

    /**
     * Method for delete request of Product entity
     *
     * @param id id of entity to be removed
     * @return true if entity removed
     */
    @DeleteMapping(params = "id")
    void deleteProduct(@RequestParam Integer id);

    /**
     * Method for requesting all of Product entities
     *
     * @return List of entities
     */
    @GetMapping
    List<ProductDto> getAllProducts();
}