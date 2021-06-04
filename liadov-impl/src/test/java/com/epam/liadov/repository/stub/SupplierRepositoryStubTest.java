package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Supplier;
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
 * SupplierRepositoryStubTest - test for {@link SupplierRepositoryStub}
 *
 * @author Aleksandr Liadov
 */
class SupplierRepositoryStubTest {
    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private SupplierRepositoryStub supplierRepository;

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

        Optional<Supplier> optionalSupplier = supplierRepository.save(supplier);

        boolean saveResult = optionalSupplier.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();

        Optional<Supplier> optionalSupplier = supplierRepository.update(supplier);

        boolean updateResult = optionalSupplier.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Supplier> optionalSupplier = supplierRepository.find(0);

        assertTrue(optionalSupplier.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();
        boolean deleteResult = supplierRepository.delete(supplier);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Supplier> all = supplierRepository.getAll();

        assertFalse(all.isEmpty());
    }
}