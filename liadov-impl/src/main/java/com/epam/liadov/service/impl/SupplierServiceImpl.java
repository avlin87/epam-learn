package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Supplier;

import com.epam.liadov.repository.SupplierRepository;
import com.epam.liadov.service.SupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SupplierServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public boolean save(Supplier supplier) {
        boolean saveResult = supplierRepository.save(supplier);
        log.trace("Supplier created: {}", saveResult);
        return saveResult;
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