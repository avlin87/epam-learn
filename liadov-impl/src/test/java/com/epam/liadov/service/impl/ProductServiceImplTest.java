package com.epam.liadov.service.impl;

import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.exception.NotFoundException;
import com.epam.liadov.repository.ProductRepository;
import com.epam.liadov.service.ProductService;
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
 * ProductServiceImplTest - test for {@link ProductServiceImpl}
 *
 * @author Aleksandr Liadov
 */
@DataJpaTest
@RunWith(SpringRunner.class)
class ProductServiceImplTest {

    private final EntityFactory factory = new EntityFactory();

    @Autowired
    private ProductService productService;

    @Test
    void saveReturnProduct() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);

        Product saveResult = productService.save(testProduct);

        assertEquals(testProduct, saveResult);
    }

    @Test
    void updateReturnProduct() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);
        testProduct = productService.save(testProduct);
        testProduct.setProductName("Updated Product");

        Product updateResult = productService.update(testProduct);

        assertEquals(testProduct, updateResult);
    }

    @Test
    void updateThrowNotFoundException() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);
        testProduct = productService.save(testProduct);
        int testProductId = testProduct.getProductId() + 999;
        testProduct.setProductId(testProductId);
        Product finalTestProduct = testProduct;

        Executable executeUpdate = () -> productService.update(finalTestProduct);

        assertThrows(NotFoundException.class, executeUpdate);
    }

    @Test
    void findReturnProduct() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);
        testProduct = productService.save(testProduct);
        int testProductId = testProduct.getProductId();

        Product foundProduct = productService.find(testProductId);

        assertEquals(testProduct, foundProduct);
    }

    @Test
    void findThrowNotFoundException() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);
        testProduct = productService.save(testProduct);
        int testProductId = testProduct.getProductId() + 999;

        Executable executeUpdate = () -> productService.find(testProductId);

        assertThrows(NotFoundException.class, executeUpdate);
    }

    @Test
    void deleteReturnTrue() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);
        testProduct = productService.save(testProduct);
        int testProductId = testProduct.getProductId();

        boolean deleteResult = productService.delete(testProductId);

        assertTrue(deleteResult);
    }

    @Test
    void deleteThrowNotFoundException() {
        Supplier testSupplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(testSupplier);
        testProduct = productService.save(testProduct);
        int testProductId = testProduct.getProductId();

        Executable executeDelete = () -> productService.delete(testProductId + 999);

        assertThrows(NotFoundException.class, executeDelete);
    }

    @Test
    void getAllReturnList() {
        List<Product> all = productService.getAll();

        assertNotNull(all);
    }

    @TestConfiguration
    static class MyTestConfiguration {
        @Bean
        public ProductService productService(ProductRepository repository) {
            return new ProductServiceImpl(repository);
        }
    }
}