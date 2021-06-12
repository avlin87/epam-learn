package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.ProductDtoToProductConverter;
import com.epam.liadov.converter.ProductToProductDtoConverter;
import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.dto.ProductDto;
import com.epam.liadov.resource.ProductResource;
import com.epam.liadov.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProductResourceImpl - class for RestController implementation of ProductResource interface
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductResourceImpl implements ProductResource {

    private final ProductService productService;
    private final ProductToProductDtoConverter toProductDtoConverter;
    private final ProductDtoToProductConverter toProductConverter;

    @Override
    public ProductDto getProduct(Integer id) {
        Product product = productService.find(id);
        log.debug("found product: {}", product);
        ProductDto productDto = toProductDtoConverter.convert(product);
        return productDto;
    }

    @Override
    public ProductDto addProduct(ProductDto productDtoToSave) {
        Product productToSave = toProductConverter.convert(productDtoToSave);
        Product savedProduct = productService.save(productToSave);
        log.debug("created product: {}", savedProduct);
        ProductDto productDto = toProductDtoConverter.convert(savedProduct);
        return productDto;
    }

    @Override
    public void deleteProduct(Integer id) {
        boolean isDeleted = productService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDtoToUpdate) {
        Product productToUpdate = toProductConverter.convert(productDtoToUpdate);
        Product updatedProduct = productService.update(productToUpdate);
        log.debug("updated productDtoToUpdate: {}", updatedProduct);
        ProductDto productDto = toProductDtoConverter.convert(updatedProduct);
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productService.getAll();
        log.trace("Get all products: {}", productList);
        List<ProductDto> productDtoList = productList.stream()
                .map(toProductDtoConverter::convert)
                .collect(Collectors.toList());
        return productDtoList;
    }
}