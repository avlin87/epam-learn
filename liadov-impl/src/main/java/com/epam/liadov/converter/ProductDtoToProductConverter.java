package com.epam.liadov.converter;

import com.epam.liadov.domain.Product;
import com.epam.liadov.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ProductDtoToProductConverter - class converter from ProductDto to Product
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class ProductDtoToProductConverter implements Converter<ProductDto, Product> {

    @Override
    public Product convert(@NotNull ProductDto productDto) {
        var product = new Product();
        int productId = productDto.getProductId();
        String productName = productDto.getProductName();
        int supplierId = productDto.getSupplierId();
        BigDecimal unitPrice = productDto.getUnitPrice();
        boolean discontinued = productDto.isDiscontinued();
        product.setProductId(productId)
                .setProductName(productName)
                .setSupplierId(supplierId)
                .setUnitPrice(unitPrice)
                .setDiscontinued(discontinued);
        log.info("convert() - convert from '{}' to {}", productDto, product);
        return product;
    }
}