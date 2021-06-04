package com.epam.liadov.service.impl;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * ProductServiceImplTest - test for {@link ProductServiceImpl}
 *
 * @author Aleksandr Liadov
 */
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    private EntityFactory factory;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void saveReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        when(productRepository.save(any())).thenReturn(Optional.ofNullable(product));

        boolean saveResult = productServiceImpl.save(product);

        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        when(productRepository.update(any())).thenReturn(Optional.ofNullable(product));

        boolean saveResult = productServiceImpl.update(product);

        assertTrue(saveResult);
    }

    @Test
    void findReturnNotNull() {
        Supplier supplier = factory.generateTestSupplier();
        Product expectedValue = factory.generateTestProduct(supplier);
        when(productRepository.find(anyInt())).thenReturn(Optional.ofNullable(expectedValue));

        Product product = productServiceImpl.find(1);

        assertNotNull(product);
    }

    @Test
    void delete() {
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        when(productRepository.delete(any())).thenReturn(true);

        boolean deleteResult = productServiceImpl.delete(product);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnList() {
        List<Product> all = productServiceImpl.getAll();

        assertNotNull(all);
    }
}