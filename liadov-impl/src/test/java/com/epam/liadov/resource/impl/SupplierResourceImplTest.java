package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.Supplier;
import com.epam.liadov.domain.factory.EntityFactory;
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
    private EntityFactory factory;

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
        when(supplierService.find(anyInt())).thenReturn(testSupplier);

        Supplier supplier = supplierResource.getSupplier(1);

        assertEquals(testSupplier, supplier);
    }

    @Test
    void addSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.save(any())).thenReturn(testSupplier);

        Supplier supplier = supplierResource.addSupplier(testSupplier);

        assertEquals(testSupplier, supplier);
    }

    @Test
    void deleteSupplier() {
        when(supplierService.delete(anyInt())).thenReturn(true);

        supplierResource.deleteSupplier(1);
    }

    @Test
    void updateSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.update(any())).thenReturn(testSupplier);

        Supplier supplier = supplierResource.updateSupplier(testSupplier);

        assertEquals(testSupplier, supplier);
    }

    @Test
    void getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        when(supplierService.getAll()).thenReturn(suppliers);

        List<Supplier> supplierList = supplierResource.getAllSuppliers();

        assertNotNull(supplierList);
    }
}