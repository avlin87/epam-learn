package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * ProductRepositoryStubTest - test for {@link ProductRepositoryStub}
 *
 * @author Aleksandr Liadov
 */
class ProductRepositoryStubTest {

    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private ProductRepositoryStub productRepository;
    private EntityFactory factory;
    private Supplier supplier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
        supplier = factory.generateTestSupplier();
        when(factoryTest.generateTestProduct(any())).thenReturn(factory.generateTestProduct(supplier));
        when(factoryTest.generateTestSupplier()).thenReturn(supplier);
    }

    @Test
    void saveReturnTrue() {
        Product product = factory.generateTestProduct(supplier);

        Optional<Product> optionalProduct = productRepository.save(product);

        boolean saveResult = optionalProduct.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Product product = factory.generateTestProduct(supplier);

        Optional<Product> optionalProduct = productRepository.update(product);

        boolean updateResult = optionalProduct.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Product> optionalProduct = productRepository.find(0);

        assertTrue(optionalProduct.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Product product = factory.generateTestProduct(supplier);
        boolean deleteResult = productRepository.delete(product);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Product> all = productRepository.getAll();

        assertFalse(all.isEmpty());
    }
}