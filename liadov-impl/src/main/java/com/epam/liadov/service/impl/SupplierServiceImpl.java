package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import com.epam.liadov.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Supplier save(Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.save(supplier);
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
        Optional<Supplier> optionalSupplier = supplierRepository.update(supplier);
        boolean updateResult = optionalSupplier.isPresent();
        log.trace("Supplier Updated: {}", updateResult);
        if (updateResult) {
            supplier = optionalSupplier.get();
            return supplier;
        }
        return new Supplier();
    }

    @Override
    public Supplier find(int primaryKey) {
        Optional<Supplier> optionalSupplier = supplierRepository.find(primaryKey);
        boolean findResult = optionalSupplier.isPresent();
        log.trace("Supplier found: {}", findResult);
        if (findResult) {
            Supplier supplier = optionalSupplier.get();
            return supplier;
        }
        return new Supplier();
    }

    @Override
    public boolean delete(Supplier supplier) {
        return supplierRepository.delete(supplier);
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.getAll();
    }
}