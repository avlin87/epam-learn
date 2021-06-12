package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.exception.BadRequestException;
import com.epam.liadov.exception.NoContentException;
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
        throw new BadRequestException("Supplier was not saved");
    }

    @Override
    public Supplier update(Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplier.getSupplierId());
        if (optionalSupplier.isPresent()) {
            optionalSupplier = Optional.ofNullable(supplierRepository.save(supplier));
            if (optionalSupplier.isPresent()) {
                supplier = optionalSupplier.get();
                log.trace("Supplier Updated: {}", supplier);
                return supplier;
            }
        }
        log.trace("Supplier was not updated");
        throw new NoContentException("Supplier does not exist");
    }

    @Override
    public Supplier find(int supplierId) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(supplierId);
        boolean findResult = optionalSupplier.isPresent();
        log.trace("Supplier found: {}", findResult);
        if (findResult) {
            Supplier supplier = optionalSupplier.get();
            return supplier;
        }
        throw new NoContentException("Supplier does not exist");
    }

    @Override
    public boolean delete(int supplierId) {
        boolean existsInDb = supplierRepository.existsById(supplierId);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            supplierRepository.deleteById(supplierId);
            boolean entityExistAfterRemove = supplierRepository.existsById(supplierId);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        throw new NoContentException("Supplier does not exist");
    }

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }
}