package com.epam.liadov.repository;

import com.epam.liadov.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
