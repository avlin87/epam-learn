package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.dto.SupplierDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * SupplierDtoToSupplierConverter - class converter from SupplierDto to Supplier
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class SupplierDtoToSupplierConverter implements Converter<SupplierDto, Supplier> {

    @Override
    public Supplier convert(@NotNull SupplierDto supplierDto) {
        var supplier = new Supplier();

        int supplierId = supplierDto.getSupplierId();
        String companyName = supplierDto.getCompanyName();
        String phone = supplierDto.getPhone();
        supplier.setSupplierId(supplierId)
                .setCompanyName(companyName)
                .setPhone(phone);
        log.info("convert() - convert from '{}' to {}", supplierDto, supplier);
        return supplier;
    }
}