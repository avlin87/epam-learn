package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ProductDtoToProductConverterTest - test for {@link ProductDtoToProductConverter}
 *
 * @author Aleksandr Liadov
 */
class ProductDtoToProductConverterTest {

    private ProductDtoToProductConverter toProductConverter;

    @BeforeEach
    public void setup() {
        toProductConverter = new ProductDtoToProductConverter();
    }

    @Test
    void convert() {
        var productDto = new ProductDto();
        productDto.setProductId(1)
                .setProductName("productNumber")
                .setSupplierId(1)
                .setDiscontinued(true)
                .setUnitPrice(BigDecimal.ONE);

        Product product = toProductConverter.convert(productDto);

        assertEquals(productDto.getProductId(), product.getProductId());
        assertEquals(productDto.getProductName(), product.getProductName());
        assertEquals(productDto.getSupplierId(), product.getSupplierId());
        assertEquals(productDto.getUnitPrice(), product.getUnitPrice());
        assertEquals(productDto.isDiscontinued(), product.isDiscontinued());
    }
}