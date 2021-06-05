package com.epam.liadov.service.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * SupplierServiceImplTest - test for {@link SupplierServiceImpl}
 *
 * @author Aleksandr Liadov
 */
class SupplierServiceImplTest {

    @Mock
    private SupplierRepository supplierRepository;
    private EntityFactory factory;

    @InjectMocks
    private SupplierServiceImpl supplierServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnSupplier() {
        Supplier supplier = factory.generateTestSupplier();
        when(supplierRepository.save(any())).thenReturn(Optional.ofNullable(supplier));

        Supplier saveResult = supplierServiceImpl.save(supplier);

        assertEquals(supplier, saveResult);
    }

    @Test
    void updateReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();
        when(supplierRepository.update(any())).thenReturn(Optional.ofNullable(supplier));

        Supplier updateResult = supplierServiceImpl.update(supplier);

        assertEquals(supplier, updateResult);
    }

    @Test
    void findReturnNotNull() {
        Supplier expectedValue = factory.generateTestSupplier();
        when(supplierRepository.find(anyInt())).thenReturn(Optional.ofNullable(expectedValue));

        Supplier supplier = supplierServiceImpl.find(1);

        assertNotNull(supplier);
    }

    @Test
    void delete() {
        Supplier supplier = factory.generateTestSupplier();
        when(supplierRepository.delete(any())).thenReturn(true);

        boolean deleteResult = supplierServiceImpl.delete(supplier);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnList() {
        List<Supplier> all = supplierServiceImpl.getAll();

        assertNotNull(all);
    }
}