package com.epam.liadov.service.stub;

import com.epam.liadov.domain.factory.EntityFactory;
import com.epam.liadov.domain.Product;
import com.epam.liadov.domain.Supplier;
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
 * ProductServiceStubTest - test for {@link ProductServiceStub}
 *
 * @author Aleksandr Liadov
 */
class ProductServiceStubTest {

    @Mock
    private EntityFactory factoryTest;

    @InjectMocks
    private ProductServiceStub productServiceStub;
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

        Optional<Product> optionalProduct = Optional.ofNullable(productServiceStub.save(product));

        boolean saveResult = optionalProduct.isPresent();
        assertTrue(saveResult);
    }

    @Test
    void updateReturnTrue() {
        Product product = factory.generateTestProduct(supplier);

        Optional<Product> optionalProduct = Optional.ofNullable(productServiceStub.update(product));

        boolean updateResult = optionalProduct.isPresent();
        assertTrue(updateResult);
    }

    @Test
    void findReturnOptionalWithObject() {
        Optional<Product> optionalProduct = Optional.ofNullable(productServiceStub.find(0));

        assertTrue(optionalProduct.isPresent());
    }

    @Test
    void deleteReturnTrue() {
        Product product = factory.generateTestProduct(supplier);
        int productId = product.getProductId();

        boolean deleteResult = productServiceStub.delete(productId);

        assertTrue(deleteResult);
    }

    @Test
    void getAllReturnListOfObjects() {
        List<Product> all = productServiceStub.getAll();

        assertFalse(all.isEmpty());
    }
}