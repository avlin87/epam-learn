package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Supplier;
import org.hibernate.engine.transaction.internal.TransactionImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    private Query query;
    private TypedQuery<Supplier> typedQuery;

    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);
        typedQuery = (TypedQuery<Supplier>) mock(QueryImpl.class);

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
        when(entityManagerMock.find(Supplier.class, testSupplier.getSupplierId())).thenReturn(testSupplier);

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

    @Test
    void getAllReturnsList() {
        Supplier supplier = factory.generateTestSupplier();
        SupplierRepository productRepository = new SupplierRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select supplier from Supplier supplier", Supplier.class)).thenReturn(typedQuery);
        List<Supplier> list = new ArrayList<>();
        list.add(supplier);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Supplier> all = productRepository.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Supplier supplier = factory.generateTestSupplier();
        SupplierRepository productRepository = new SupplierRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select supplier from Supplier supplier", Supplier.class)).thenThrow(IllegalArgumentException.class);
        List<Supplier> list = new ArrayList<>();
        list.add(supplier);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Supplier> all = productRepository.getAll();

        assertTrue(all.isEmpty());
    }
}