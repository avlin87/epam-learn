package com.epam.liadov.repository;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
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
 * ProductRepositoryTest test for {@link ProductRepository}
 *
 * @author Aleksandr Liadov
 */
class ProductRepositoryTest {

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
}