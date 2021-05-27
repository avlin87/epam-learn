package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import com.epam.liadov.service.SupplierService;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

/**
 * SupplierServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class SupplierServiceImpl implements SupplierService {

    private static final EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");
    private final SupplierRepository supplierRepository = new SupplierRepository(entityPU);

    @Override
    public Supplier save(Supplier supplier) {
        Supplier createdSupplier = new Supplier();
        Optional<Supplier> optionalSupplier = supplierRepository.save(supplier);
        if (optionalSupplier.isPresent()) {
            createdSupplier = optionalSupplier.get();
            log.trace("Supplier created successfully");
        } else {
            log.trace("Supplier was not created");
        }
        return createdSupplier;
    }

    @Override
    public boolean update(Supplier supplier) {
        return supplierRepository.update(supplier);
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