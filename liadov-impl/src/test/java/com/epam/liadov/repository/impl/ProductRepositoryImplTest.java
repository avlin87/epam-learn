package com.epam.liadov.repository.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ProductRepositoryImplTest test for {@link ProductRepositoryImpl}
 *
 * @author Aleksandr Liadov
 */
class ProductRepositoryImplTest {

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Product> typedQuery;

    @Mock
    private EntityTransaction entityTransaction;

    @InjectMocks
    private ProductRepositoryImpl productRepositoryImpl;

    private EntityFactory factory;
    private Supplier supplier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        supplier = factory.generateTestSupplier();
    }

    @Test
    void saveReturnsOptionalWithCustomer() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Product testProduct = factory.generateTestProduct(supplier);

        Optional<Product> optionalProduct = productRepositoryImpl.save(testProduct);

        boolean saveResult = optionalProduct.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void saveReturnsFalse() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        Optional<Product> optionalProduct = productRepositoryImpl.save(null);
        boolean save = optionalProduct.isPresent();

        assertFalse(save);
    }

    @Test
    void updateReturnOptionalWithCustomer() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.find(Product.class, testProduct.getProductId())).thenReturn(testProduct);
        when(entityManagerMock.merge(any())).thenReturn(testProduct);

        Optional<Product> optionalProduct = productRepositoryImpl.update(testProduct);

        boolean productUpdated = optionalProduct.isPresent();
        assertTrue(productUpdated);
    }

    @Test
    void updateReturnEmptyOptional() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.merge(testProduct)).thenThrow(IllegalArgumentException.class);

        Optional<Product> optionalProduct = productRepositoryImpl.update(testProduct);

        boolean productUpdated = optionalProduct.isPresent();
        assertFalse(productUpdated);
    }

    @Test
    void updateReturnEmptyOptionalDueToException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.find(Product.class, testProduct.getProductId())).thenReturn(testProduct);
        when(entityManagerMock.merge(testProduct)).thenThrow(IllegalArgumentException.class);

        Optional<Product> optionalProduct = productRepositoryImpl.update(testProduct);

        boolean productUpdated = optionalProduct.isPresent();
        assertFalse(productUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullProduct() {
        Executable executable = () -> productRepositoryImpl.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Product> optionalProduct = productRepositoryImpl.find(0);

        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Product.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Product> optionalProduct = productRepositoryImpl.find(1);

        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Product testProduct = factory.generateTestProduct(supplier);

        boolean deleteResult = productRepositoryImpl.delete(testProduct);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        when(entityManagerMock.getTransaction()).thenReturn(entityTransaction);
        Product testProduct = factory.generateTestProduct(supplier);
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = productRepositoryImpl.delete(testProduct);

        assertFalse(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.createQuery("select product from Product product", Product.class)).thenReturn(typedQuery);
        List<Product> list = new ArrayList<>();
        list.add(testProduct);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Product> all = productRepositoryImpl.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.createQuery("select product from Product product", Product.class)).thenThrow(IllegalArgumentException.class);
        List<Product> list = new ArrayList<>();
        list.add(testProduct);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Product> all = productRepositoryImpl.getAll();

        assertTrue(all.isEmpty());
    }
}