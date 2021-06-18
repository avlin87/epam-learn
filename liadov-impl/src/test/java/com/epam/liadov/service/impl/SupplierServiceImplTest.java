package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.exception.NotFoundException;
import com.epam.liadov.repository.SupplierRepository;
import com.epam.liadov.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SupplierServiceImplTest - test for {@link SupplierServiceImpl}
 *
 * @author Aleksandr Liadov
 */
@DataJpaTest
@RunWith(SpringRunner.class)
class SupplierServiceImplTest {

    private final EntityFactory factory = new EntityFactory();

    @Autowired
    private SupplierService supplierService;

    @Test
    void saveReturnSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();

        Supplier saveResult = supplierService.save(testSupplier);

        assertEquals(testSupplier, saveResult);
    }

    @Test
    void updateReturnSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        testSupplier = supplierService.save(testSupplier);
        testSupplier.setCompanyName("Updated Supplier")
                .setPhone("22222");

        Supplier updateResult = supplierService.update(testSupplier);

        assertEquals(testSupplier, updateResult);
    }

    @Test
    void updateThrowNotFoundException() {
        Supplier testSupplier = factory.generateTestSupplier();
        testSupplier = supplierService.save(testSupplier);
        int testSupplierId = testSupplier.getSupplierId() + 999;
        testSupplier.setSupplierId(testSupplierId);
        Supplier finalTestSupplier = testSupplier;

        Executable executeUpdate = () -> supplierService.update(finalTestSupplier);

        assertThrows(NotFoundException.class, executeUpdate);
    }

    @Test
    void findReturnSupplier() {
        Supplier testSupplier = factory.generateTestSupplier();
        testSupplier = supplierService.save(testSupplier);
        int testSupplierId = testSupplier.getSupplierId();

        Supplier foundSupplier = supplierService.find(testSupplierId);

        assertEquals(testSupplier, foundSupplier);
    }

    @Test
    void findThrowNotFoundException() {
        Supplier testSupplier = factory.generateTestSupplier();
        testSupplier = supplierService.save(testSupplier);
        int testSupplierId = testSupplier.getSupplierId() + 999;

        Executable executeUpdate = () -> supplierService.find(testSupplierId);

        assertThrows(NotFoundException.class, executeUpdate);
    }

    @Test
    void deleteReturnTrue() {
        Supplier testSupplier = factory.generateTestSupplier();
        testSupplier = supplierService.save(testSupplier);
        int testSupplierId = testSupplier.getSupplierId();

        boolean deleteResult = supplierService.delete(testSupplierId);

        assertTrue(deleteResult);
    }

    @Test
    void deleteThrowNotFoundException() {
        Supplier testSupplier = factory.generateTestSupplier();
        testSupplier = supplierService.save(testSupplier);
        int testSupplierId = testSupplier.getSupplierId();

        Executable executeDelete = () -> supplierService.delete(testSupplierId + 999);

        assertThrows(NotFoundException.class, executeDelete);
    }

    @Test
    void getAllReturnList() {
        List<Supplier> all = supplierService.getAll();

        assertNotNull(all);
    }

    @TestConfiguration
    static class MyTestConfiguration {
        @Bean
        public SupplierService supplierService(SupplierRepository repository) {
            return new SupplierServiceImpl(repository);
        }
    }
}