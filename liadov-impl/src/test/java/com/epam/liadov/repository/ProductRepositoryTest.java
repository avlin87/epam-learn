package com.epam.liadov.repository;

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
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
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

    @Mock
    private EntityManager entityManagerMock;

    @Mock
    private TypedQuery<Product> typedQuery;

    @InjectMocks
    private ProductRepository productRepository;

    private EntityFactory factory;
    private Supplier supplier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        supplier = factory.generateTestSupplier();
    }

    @Test
    void saveReturnsTrue() {
        Product testProduct = factory.generateTestProduct(supplier);

        boolean saveResult = productRepository.save(testProduct);

        assertTrue(saveResult);
    }

    @Test
    void saveReturnsFalse() {
        Mockito.doThrow(new PersistenceException()).when(entityManagerMock).persist(null);

        boolean save = productRepository.save(null);

        assertFalse(save);
    }

    @Test
    void updateReturnThue() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.find(Product.class, testProduct.getProductId())).thenReturn(testProduct);

        boolean productUpdated = productRepository.update(testProduct);

        assertTrue(productUpdated);
    }

    @Test
    void updateReturnFalse() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.merge(testProduct)).thenThrow(IllegalArgumentException.class);

        boolean productUpdated = productRepository.update(testProduct);

        assertFalse(productUpdated);
    }

    @Test
    void updateReturnFalseDueToException() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.find(Product.class, testProduct.getProductId())).thenReturn(testProduct);
        when(entityManagerMock.merge(testProduct)).thenThrow(IllegalArgumentException.class);

        boolean productUpdated = productRepository.update(testProduct);

        assertFalse(productUpdated);
    }

    @Test
    void updateTrowsExceptionIfReceivesNullProduct() {
        Executable executable = () -> productRepository.update(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    void findReturnsEmptyOptional() {
        Optional<Product> optionalProduct = productRepository.find(0);

        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void findProcessException() {
        when(entityManagerMock.find(Product.class, 1)).thenThrow(IllegalArgumentException.class);

        Optional<Product> optionalProduct = productRepository.find(1);

        assertFalse(optionalProduct.isPresent());
    }

    @Test
    void deleteReturnsTrue() {
        Product testProduct = factory.generateTestProduct(supplier);

        boolean deleteResult = productRepository.delete(testProduct);

        assertTrue(deleteResult);
    }

    @Test
    void deleteProcessException() {
        Product testProduct = factory.generateTestProduct(supplier);
        doThrow(IllegalArgumentException.class).when(entityManagerMock).remove(any());

        boolean deleteResult = productRepository.delete(testProduct);

        assertFalse(deleteResult);
    }

    @Test
    void getAllReturnsList() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.createQuery("select product from Product product", Product.class)).thenReturn(typedQuery);
        List<Product> list = new ArrayList<>();
        list.add(testProduct);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Product> all = productRepository.getAll();

        assertFalse(all.isEmpty());
    }

    @Test
    void getAllReturnsProcessException() {
        Product testProduct = factory.generateTestProduct(supplier);
        when(entityManagerMock.createQuery("select product from Product product", Product.class)).thenThrow(IllegalArgumentException.class);
        List<Product> list = new ArrayList<>();
        list.add(testProduct);
        when(typedQuery.getResultList()).thenReturn(list);

        List<Product> all = productRepository.getAll();

        assertTrue(all.isEmpty());
    }
}