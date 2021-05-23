package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * SupplierRepositoryTest test for {@link SupplierRepository}
 *
 * @author Aleksandr Liadov
 */
class SupplierRepositoryTest {

    private EntityFactory factory;
    private EntityManagerFactory entityManagerFactoryMock;
    private EntityManager entityManagerMock;
    private EntityTransaction transactionMock;


    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);

        factory = new EntityFactory();
    }

    @Test
    void saveReturnsSupplierObject() {
        Supplier testSupplier = factory.generateTestSupplier();
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        Optional<Supplier> supplierOptional = supplierRepository.save(testSupplier);

        assertTrue(supplierOptional.get() instanceof Supplier);
    }

    @Test
    void saveReturnsEmptyObject() {
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        Optional<Supplier> supplierOptional = supplierRepository.save(null);

        assertFalse(supplierOptional.isPresent());
    }

    @Test
    void saveProcessException() {
        doThrow(IllegalArgumentException.class).when(entityManagerMock).persist(null);
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        Optional<Supplier> supplierOptional = supplierRepository.save(null);

        assertFalse(supplierOptional.isPresent());
    }

    @Test
    void updateReturnThue() {
        Supplier testSupplier = factory.generateTestSupplier();
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        boolean supplierUpdated = supplierRepository.update(testSupplier);

        assertTrue(supplierUpdated);
    }

    @Test
    void updateReturnFalse() {
        Supplier testSupplier = factory.generateTestSupplier();
        when(entityManagerMock.merge(testSupplier)).thenThrow(TransactionRequiredException.class);
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        boolean customerUpdated = supplierRepository.update(testSupplier);

        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullSupplier() {
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        Executable executable = () -> supplierRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        Optional<Supplier> optionalSupplier = supplierRepository.find(0);

        assertFalse(optionalSupplier.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Supplier.class, 1)).thenThrow(IllegalArgumentException.class);
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        Optional<Supplier> optionalSupplier = supplierRepository.find(1);

        assertFalse(optionalSupplier.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Supplier testSupplier = factory.generateTestSupplier();
        SupplierRepository supplierRepository = new SupplierRepository(entityManagerFactoryMock);

        boolean deleteResult = supplierRepository.delete(testSupplier);

        assertTrue(deleteResult);
    }
}