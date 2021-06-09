package com.epam.liadov.service.impl;

import com.epam.liadov.domain.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import com.epam.liadov.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SupplierServiceImpl - Service for operations with Supplier repository
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!local")
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier save(Supplier supplier) {
        Optional<Supplier> optionalSupplier = Optional.ofNullable(supplierRepository.save(supplier));
        boolean saveResult = optionalSupplier.isPresent();
        log.trace("Supplier created: {}", saveResult);
        if (saveResult) {
            supplier = optionalSupplier.get();
            return supplier;
        }
        return new Supplier();
    }

    @Override
    public Supplier update(Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplier.getSupplierId());
        if (optionalSupplier.isPresent()) {
            optionalSupplier = Optional.ofNullable(supplierRepository.save(supplier));
        }
        if (optionalSupplier.isPresent()) {
            supplier = optionalSupplier.get();
            log.trace("Supplier Updated: {}", supplier);
            return supplier;
        }
        log.trace("Supplier was not updated");
        return new Supplier();
    }

    @Override
    public Supplier find(int primaryKey) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(primaryKey);
        boolean findResult = optionalSupplier.isPresent();
        log.trace("Supplier found: {}", findResult);
        if (findResult) {
            Supplier supplier = optionalSupplier.get();
            return supplier;
        }
        return new Supplier();
    }

    @Override
    public boolean delete(int primaryKey) {
        boolean existsInDb = supplierRepository.existsById(primaryKey);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            supplierRepository.deleteById(primaryKey);
            boolean entityExistAfterRemove = supplierRepository.existsById(primaryKey);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        return false;
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }
}