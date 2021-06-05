package com.epam.liadov.repository.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * SupplierRepositoryTest test for {@link SupplierRepositoryImpl}
 *
 * @author Aleksandr Liadov
 */
class SupplierRepositoryImplTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Supplier> typedQuery;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private SupplierRepositoryImpl supplierRepositoryImpl;

    private EntityFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnsOptionalWithSupplier() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Supplier testSupplier = factory.generateTestSupplier();

        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.save(testSupplier);

        boolean saveResult = optionalSupplier.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void saveReturnsEmptyOptional() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.save(null);

        boolean save = optionalSupplier.isPresent();
        assertFalse(save);
    }

    @Test
    void updateReturnOptionalWithSupplier() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Supplier testSupplier = factory.generateTestSupplier();
        when(entityManagerMock.find(Supplier.class, testSupplier.getSupplierId())).thenReturn(testSupplier);
        when(entityManagerMock.merge(any())).thenReturn(testSupplier);

        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.update(testSupplier);

        boolean supplierUpdated = optionalSupplier.isPresent();
        assertTrue(supplierUpdated);
    }

    @Test
    void updateReturnEmptyOptional() {
        Supplier testSupplier = factory.generateTestSupplier();
        when(entityManagerMock.merge(testSupplier)).thenThrow(TransactionRequiredException.class);

        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.update(testSupplier);

        boolean supplierUpdated = optionalSupplier.isPresent();
        assertFalse(supplierUpdated);
    }

    @Test
    void updateReturnEmptyOptionalDueToException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Supplier testSupplier = factory.generateTestSupplier();
        when(entityManagerMock.find(Supplier.class, testSupplier.getSupplierId())).thenReturn(testSupplier);
        when(entityManagerMock.merge(testSupplier)).thenThrow(IllegalArgumentException.class);

        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.update(testSupplier);

        boolean productUpdated = optionalSupplier.isPresent();
        assertFalse(productUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullSupplier() {
        Executable executable = () -> supplierRepositoryImpl.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.find(0);

        assertFalse(optionalSupplier.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Supplier.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Supplier> optionalSupplier = supplierRepositoryImpl.find(1);

        assertFalse(optionalSupplier.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Supplier testSupplier = factory.generateTestSupplier();

        boolean deleteResult = supplierRepositoryImpl.delete(testSupplier);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Supplier testSupplier = factory.generateTestSupplier();
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = supplierRepositoryImpl.delete(testSupplier);

        assertFalse(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Supplier testSupplier = factory.generateTestSupplier();
        when(entityManagerMock.createQuery("select supplier from Supplier supplier", Supplier.class)).thenReturn(typedQuery);
        List<Supplier> list = new ArrayList<>();
        list.add(testSupplier);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Supplier> all = supplierRepositoryImpl.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Supplier testSupplier = factory.generateTestSupplier();
        when(entityManagerMock.createQuery("select supplier from Supplier supplier", Supplier.class)).thenThrow(IllegalArgumentException.class);
        List<Supplier> list = new ArrayList<>();
        list.add(testSupplier);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Supplier> all = supplierRepositoryImpl.getAll();

        assertTrue(all.isEmpty());
    }
}