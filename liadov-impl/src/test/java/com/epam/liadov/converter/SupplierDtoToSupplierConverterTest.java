package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.dto.SupplierDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * SupplierDtoToSupplierConverterTest - test for {@link SupplierDtoToSupplierConverter}
 *
 * @author Aleksandr Liadov
 */
class SupplierDtoToSupplierConverterTest {

    private SupplierDtoToSupplierConverter toSupplierConverter;

    @BeforeEach
    public void setup() {
        toSupplierConverter = new SupplierDtoToSupplierConverter();
    }

    @Test
    void convert() {
        var supplierDto = new SupplierDto();
        supplierDto.setSupplierId(1)
                .setCompanyName("Company Name")
                .setPhone("1111-222");

        Supplier supplier = toSupplierConverter.convert(supplierDto);

        assertEquals(supplier.getSupplierId(), supplierDto.getSupplierId());
        assertEquals(supplier.getCompanyName(), supplierDto.getCompanyName());
        assertEquals(supplier.getPhone(), supplierDto.getPhone());
    }
}