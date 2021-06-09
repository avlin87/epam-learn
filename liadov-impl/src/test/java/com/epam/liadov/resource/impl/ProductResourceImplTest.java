package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.ProductDtoToProductConverter;
import com.epam.liadov.converter.ProductToProductDtoConverter;
import com.epam.liadov.domain.Product;
import com.epam.liadov.domain.Supplier;
import com.epam.liadov.domain.factory.EntityFactory;
import com.epam.liadov.dto.ProductDto;
import com.epam.liadov.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * ProductResourceImplTest - test for {@link ProductResourceImpl}
 *
 * @author Aleksandr Liadov
 */
class ProductResourceImplTest {

    @Mock
    private ProductServiceImpl productService;
    @Mock
    private ProductToProductDtoConverter productToProductDtoConverter;
    @Mock
    private ProductDtoToProductConverter productDtoToProductConverter;

    private EntityFactory factory;
    private ProductToProductDtoConverter toProductDtoConverter = new ProductToProductDtoConverter();

    @InjectMocks
    private ProductResourceImpl productResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new EntityFactory();
    }

    @Test
    void getProduct() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductDto testProductDto = toProductDtoConverter.convert(testProduct);
        when(productService.find(anyInt())).thenReturn(testProduct);
        when(productToProductDtoConverter.convert(any())).thenReturn(testProductDto);

        ProductDto productDto = productResource.getProduct(1);

        assertEquals(testProductDto, productDto);
    }

    @Test
    void addProduct() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductDto testProductDto = toProductDtoConverter.convert(testProduct);
        when(productToProductDtoConverter.convert(any())).thenReturn(testProductDto);
        when(productDtoToProductConverter.convert(any())).thenReturn(testProduct);
        when(productService.save(any())).thenReturn(testProduct);

        ProductDto productDto = productResource.addProduct(testProductDto);

        assertEquals(testProductDto, productDto);
    }

    @Test
    void deleteProduct() {
        when(productService.delete(anyInt())).thenReturn(true);

        productResource.deleteProduct(1);
    }

    @Test
    void updateProduct() {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductDto testProductDto = toProductDtoConverter.convert(testProduct);
        when(productService.update(any())).thenReturn(testProduct);
        when(productToProductDtoConverter.convert(any())).thenReturn(testProductDto);

        ProductDto updateProduct = productResource.updateProduct(testProductDto);

        assertEquals(testProductDto, updateProduct);
    }

    @Test
    void getAllProducts() {
        List<Product> products = new ArrayList<>();
        when(productService.getAll()).thenReturn(products);

        List<ProductDto> productList = productResource.getAllProducts();

        assertNotNull(productList);
    }
}