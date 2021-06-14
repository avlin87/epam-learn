package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.dto.SupplierDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * SupplierToSupplierDtoConverter - class converter from Supplier to SupplierDto
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class SupplierToSupplierDtoConverter implements Converter<Supplier, SupplierDto> {

    @Override
    public SupplierDto convert(@NotNull Supplier supplier) {
        var supplierDto = new SupplierDto();

        int supplierId = supplier.getSupplierId();
        String companyName = supplier.getCompanyName();
        String phone = supplier.getPhone();
        supplierDto.setSupplierId(supplierId)
                .setCompanyName(companyName)
                .setPhone(phone);
        log.trace("convert() - convert from '{}' to {}", supplier, supplierDto);
        return supplierDto;
    }
}