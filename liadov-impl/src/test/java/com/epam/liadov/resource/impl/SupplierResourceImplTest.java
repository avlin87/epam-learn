package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.SupplierDtoToSupplierConverter;
import com.epam.liadov.converter.SupplierToSupplierDtoConverter;
import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.dto.SupplierDto;
import com.epam.liadov.service.impl.SupplierServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * SupplierResourceImplTest - test for {@link SupplierResourceImpl}
 *
 * @author Aleksandr Liadov
 */
class SupplierResourceImplTest {

    @Mock
    private SupplierServiceImpl supplierService;
    @Mock
    private SupplierToSupplierDtoConverter supplierToSupplierDtoConverter;
    @Mock
    private SupplierDtoToSupplierConverter supplierDtoToSupplierConverter;


    private EntityFactory factory;
    private SupplierToSupplierDtoConverter toSupplierDtoConverter = new SupplierToSupplierDtoConverter();

    @InjectMocks
    private SupplierResourceImpl supplierResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void getSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        SupplierDto testSupplierDto = toSupplierDtoConverter.convert(testSupplier);
        when(supplierService.find(anyInt())).thenReturn(testSupplier);
        when(supplierToSupplierDtoConverter.convert(any())).thenReturn(testSupplierDto);

        SupplierDto supplierDto = supplierResource.getSupplier(1);

        assertEquals(testSupplierDto, supplierDto);
    }

    @Test
    void addSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        SupplierDto testSupplierDto = toSupplierDtoConverter.convert(testSupplier);
        when(supplierService.save(any())).thenReturn(testSupplier);
        when(supplierToSupplierDtoConverter.convert(any())).thenReturn(testSupplierDto);

        SupplierDto supplierDto = supplierResource.addSupplier(testSupplierDto);

        assertEquals(testSupplierDto, supplierDto);
    }

    @Test
    void deleteSupplier() {
        when(supplierService.delete(anyInt())).thenReturn(true);

        supplierResource.deleteSupplier(1);
    }

    @Test
    void updateSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        SupplierDto testSupplierDto = toSupplierDtoConverter.convert(testSupplier);
        when(supplierService.update(any())).thenReturn(testSupplier);
        when(supplierToSupplierDtoConverter.convert(any())).thenReturn(testSupplierDto);

        SupplierDto supplierDto = supplierResource.updateSupplier(testSupplierDto);

        assertEquals(testSupplierDto, supplierDto);
    }

    @Test
    void getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        when(supplierService.getAll()).thenReturn(suppliers);

        List<SupplierDto> supplierList = supplierResource.getAllSuppliers();

        assertNotNull(supplierList);
    }
}