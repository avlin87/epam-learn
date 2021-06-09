package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.Supplier;
import com.epam.liadov.resource.SupplierResource;
import com.epam.liadov.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SupplierResourceImpl - class for RestController implementation of SupplierResource interface
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SupplierResourceImpl implements SupplierResource<Supplier> {

    private final SupplierService supplierService;

    @Override
    public Supplier getSupplier(Integer id) {
        Supplier supplier = supplierService.find(id);
        log.debug("found supplier: {}", supplier);
        return supplier;
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {
        Supplier savedSupplier = supplierService.save(supplier);
        log.debug("created supplier: {}", savedSupplier);
        return savedSupplier;
    }

    @Override
    public void deleteSupplier(Integer id) {
        boolean isDeleted = supplierService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        Supplier updatedSupplier = supplierService.update(supplier);
        log.debug("updated supplier: {}", updatedSupplier);
        return updatedSupplier;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        List<Supplier> supplierList = supplierService.getAll();
        log.trace("Get all suppliers: {}", supplierList);
        return supplierList;
    }
}