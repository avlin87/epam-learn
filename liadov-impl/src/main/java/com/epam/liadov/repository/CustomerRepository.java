package com.epam.liadov.repository;

import com.epam.liadov.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CustomerRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
