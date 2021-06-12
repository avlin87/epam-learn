package com.epam.liadov.resource;

import com.epam.liadov.dto.OrderDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OrderResource - interface for Rest operations with Order entity
 *
 * @author Aleksandr Liadov
 */
@RequestMapping("/order")
public interface OrderResource {

    /**
     * Method for requesting Order by id
     *
     * @param id - id of requested entity
     * @return entity object
     */
    @GetMapping("/{id}")
    OrderDto getOrder(@PathVariable Integer id);

    /**
     * Method for getting list of all Orders related to specific Customer by customerId
     *
     * @param customerId - customerId value
     * @return List of entities
     */
    @GetMapping("/customer/{customerId}")
    List<OrderDto> getByCustomerId(@PathVariable Integer customerId);

    /**
     * Method parse json POST as Order object
     *
     * @param order entity object
     * @return entity object
     */
    @PostMapping
    OrderDto addOrder(@RequestBody OrderDto order);

    /**
     * Method parse json PUT as Order object
     *
     * @param order entity object
     * @return entity object
     */
    @PutMapping
    OrderDto updateOrder(@RequestBody OrderDto order);

    /**
     * Method for delete request of Order entity
     *
     * @param id id of entity to be removed
     * @return true if entity removed
     */
    @DeleteMapping("/{id}")
    void deleteOrder(@PathVariable Integer id);

    /**
     * Method for requesting all of Order entities
     *
     * @return List of entities
     */
    @GetMapping
    List<OrderDto> getAllOrders();
}