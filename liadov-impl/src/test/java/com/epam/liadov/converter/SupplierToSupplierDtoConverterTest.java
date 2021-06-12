package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.dto.SupplierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * SupplierToSupplierDtoConverterTest - test for {@link SupplierToSupplierDtoConverter}
 *
 * @author Aleksandr Liadov
 */
class SupplierToSupplierDtoConverterTest {

    private SupplierToSupplierDtoConverter toSupplierDtoConverter;

    @BeforeEach
    public void setup() {
        toSupplierDtoConverter = new SupplierToSupplierDtoConverter();
    }

    @Test
    void convert() {
        var supplier = new Supplier();
        supplier.setSupplierId(1)
                .setCompanyName("Company Name")
                .setPhone("1111-222");

        SupplierDto supplierDto = toSupplierDtoConverter.convert(supplier);

        assertEquals(supplierDto.getSupplierId(), supplier.getSupplierId());
        assertEquals(supplierDto.getCompanyName(), supplier.getCompanyName());
        assertEquals(supplierDto.getPhone(), supplier.getPhone());
    }
}