package com.epam.liadov.repository;

import com.epam.liadov.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Method of all Order objects by CustomerId
     *
     * @return list
     */
    List<Order> findAllByCustomerId(Integer customerId);
}
