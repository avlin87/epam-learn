package com.epam.liadov.service.stub;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * SupplierServiceStubTest - test for {@link SupplierServiceStub}
 *
 * @author Aleksandr Liadov
 */
class SupplierServiceStubTest {
    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private SupplierServiceStub supplierServiceStub;

    private EntityFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        when(factoryTest.generateTestSupplier()).thenReturn(factory.generateTestSupplier());
    }

    @Test
    void saveReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();

        Optional<Supplier> optionalSupplier = Optional.ofNullable(supplierServiceStub.save(supplier));

        boolean saveResult = optionalSupplier.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();

        Optional<Supplier> optionalSupplier = Optional.ofNullable(supplierServiceStub.update(supplier));

        boolean updateResult = optionalSupplier.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Supplier> optionalSupplier = Optional.ofNullable(supplierServiceStub.find(0));

        assertTrue(optionalSupplier.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();
        int supplierId = supplier.getSupplierId();

        boolean deleteResult = supplierServiceStub.delete(supplierId);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Supplier> all = supplierServiceStub.getAll();

        assertFalse(all.isEmpty());
    }
}