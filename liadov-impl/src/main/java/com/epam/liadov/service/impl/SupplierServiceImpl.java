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
    public boolean save(Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.save(supplier);
        boolean saveResult = optionalSupplier.isPresent();
        log.trace("Supplier created: {}", saveResult);
        return saveResult;
    }

    @Override
    public boolean update(Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.update(supplier);
        return optionalSupplier.isPresent();
    }

    @Override
    public Supplier find(int primaryKey) {
        return supplierRepository.find(primaryKey).orElse(new Supplier());
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