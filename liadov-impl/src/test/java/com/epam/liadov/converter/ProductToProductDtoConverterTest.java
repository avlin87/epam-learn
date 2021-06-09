package com.epam.liadov.converter;

import com.epam.liadov.domain.Product;
import com.epam.liadov.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ProductToProductDtoConverterTest - test for {@link ProductToProductDtoConverter}
 *
 * @author Aleksandr Liadov
 */
class ProductToProductDtoConverterTest {


    private ProductToProductDtoConverter toProductDtoConverter;

    @BeforeEach
    public void setup() {
        toProductDtoConverter = new ProductToProductDtoConverter();
    }

    @Test
    void convert() {
        var product = new Product();
        product.setProductId(1)
                .setProductName("productNumber")
                .setSupplierId(1)
                .setDiscontinued(true)
                .setUnitPrice(BigDecimal.ONE);

        ProductDto productDto = toProductDtoConverter.convert(product);

        assertEquals(product.getProductId(), productDto.getProductId());
        assertEquals(product.getProductName(), productDto.getProductName());
        assertEquals(product.getSupplierId(), productDto.getSupplierId());
        assertEquals(product.getUnitPrice(), productDto.getUnitPrice());
        assertEquals(product.isDiscontinued(), productDto.isDiscontinued());
    }
}