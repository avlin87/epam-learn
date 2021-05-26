package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Product;
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
 * ProductRepositoryTest test for {@link ProductRepository}
 *
 * @author Aleksandr Liadov
 */
class ProductRepositoryTest {

    private EntityFactory factory;
    private EntityManagerFactory entityManagerFactoryMock;
    private EntityManager entityManagerMock;
    private EntityTransaction transactionMock;
    private Query query;
    private TypedQuery<Product> typedQuery;

    @BeforeEach
    public void prerequisite() {
        entityManagerFactoryMock = mock(SessionFactoryImpl.class);
        entityManagerMock = mock(SessionImpl.class);
        transactionMock = mock(TransactionImpl.class);
        query = mock(NativeQueryImplementor.class);
        typedQuery = (TypedQuery<Product>) mock(QueryImpl.class);

        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityManagerMock);
        when(entityManagerMock.getTransaction()).thenReturn(transactionMock);

        factory = new EntityFactory();
    }

    @Test
    void saveReturnsProductObject() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        Optional<Product> productOptional = productRepository.save(testProduct);

        assertTrue(productOptional.get() instanceof Product);
    }

    @Test
    void saveReturnsEmptyObject() {
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        Optional<Product> productOptional = productRepository.save(null);

        assertFalse(productOptional.isPresent());
    }

    @Test
    void saveProcessException() {
        doThrow(IllegalArgumentException.class).when(entityManagerMock).persist(null);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        Optional<Product> productOptional = productRepository.save(null);

        assertFalse(productOptional.isPresent());
    }

    @Test
    void updateReturnThue() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);
        when(entityManagerMock.find(Product.class, testProduct.getProductId())).thenReturn(testProduct);

        boolean productUpdated = productRepository.update(testProduct);

        assertTrue(productUpdated);
    }

    @Test
    void updateReturnFalse() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.merge(testProduct)).thenThrow(TransactionRequiredException.class);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        boolean customerUpdated = productRepository.update(testProduct);

        assertFalse(customerUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullProduct() {
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        Executable executable = () -> productRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        Optional<Product> optionalProduct = productRepository.find(0);

        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Product.class, 1)).thenThrow(IllegalArgumentException.class);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        Optional<Product> optionalProduct = productRepository.find(1);

        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);

        boolean deleteResult = productRepository.delete(testProduct);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select product from Product product", Product.class)).thenReturn(typedQuery);
        List<Product> list = new ArrayList<>();
        list.add(product);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Product> all = productRepository.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        ProductRepository productRepository = new ProductRepository(entityManagerFactoryMock);
        when(entityManagerMock.createQuery("select product from Product product", Product.class)).thenThrow(IllegalArgumentException.class);
        List<Product> list = new ArrayList<>();
        list.add(product);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Product> all = productRepository.getAll();

        assertTrue(all.isEmpty());
    }
}