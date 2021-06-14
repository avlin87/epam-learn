package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ProductToProductDtoConverter - class converter from Product to ProductDto
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class ProductToProductDtoConverter implements Converter<Product, ProductDto> {

    @Override
    public ProductDto convert(@NotNull Product product) {
        var productDto = new ProductDto();
        int productId = product.getProductId();
        String productName = product.getProductName();
        int supplierId = product.getSupplierId();
        BigDecimal unitPrice = product.getUnitPrice();
        boolean discontinued = product.isDiscontinued();
        productDto.setProductId(productId)
                .setProductName(productName)
                .setSupplierId(supplierId)
                .setUnitPrice(unitPrice)
                .setDiscontinued(discontinued);
        log.trace("convert() - convert from '{}' to {}", product, productDto);
        return productDto;
    }
}