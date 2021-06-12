package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.SupplierDtoToSupplierConverter;
import com.epam.liadov.converter.SupplierToSupplierDtoConverter;
import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.dto.SupplierDto;
import com.epam.liadov.resource.SupplierResource;
import com.epam.liadov.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SupplierResourceImpl - class for RestController implementation of SupplierResource interface
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SupplierResourceImpl implements SupplierResource {

    private final SupplierService supplierService;
    private final SupplierToSupplierDtoConverter toSupplierDtoConverter;
    private final SupplierDtoToSupplierConverter toSupplierConverter;

    @Override
    public SupplierDto getSupplier(Integer id) {
        Supplier supplier = supplierService.find(id);
        log.debug("found supplier: {}", supplier);
        SupplierDto supplierDto = toSupplierDtoConverter.convert(supplier);
        return supplierDto;
    }

    @Override
    public SupplierDto addSupplier(SupplierDto supplierDto) {
        Supplier supplier = toSupplierConverter.convert(supplierDto);
        Supplier savedSupplier = supplierService.save(supplier);
        log.debug("created supplier: {}", savedSupplier);
        supplierDto = toSupplierDtoConverter.convert(savedSupplier);
        return supplierDto;
    }

    @Override
    public void deleteSupplier(Integer id) {
        boolean isDeleted = supplierService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public SupplierDto updateSupplier(SupplierDto supplierDto) {
        Supplier supplier = toSupplierConverter.convert(supplierDto);
        Supplier updatedSupplier = supplierService.update(supplier);
        log.debug("updated supplier: {}", updatedSupplier);
        supplierDto = toSupplierDtoConverter.convert(updatedSupplier);
        return supplierDto;
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        List<Supplier> supplierList = supplierService.getAll();
        log.trace("Get all suppliers: {}", supplierList);
        List<SupplierDto> supplierDtoList = supplierList.stream()
                .map(toSupplierDtoConverter::convert)
                .collect(Collectors.toList());
        return supplierDtoList;
    }
}