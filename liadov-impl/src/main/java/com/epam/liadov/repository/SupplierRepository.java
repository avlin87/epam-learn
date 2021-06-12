package com.epam.liadov.repository;

import com.epam.liadov.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SupplierRepository - interface for Repository operations
 *
 * @author Aleksandr Liadov
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}
