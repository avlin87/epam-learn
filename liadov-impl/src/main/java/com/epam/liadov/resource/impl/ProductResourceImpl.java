package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.ProductDtoToProductConverter;
import com.epam.liadov.converter.ProductToProductDtoConverter;
import com.epam.liadov.domain.Product;
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
    private final ProductToProductDtoConverter productToProductDtoConverter;
    private final ProductDtoToProductConverter productDtoToProductConverter;

    @Override
    public ProductDto getProduct(Integer id) {
        Product product = productService.find(id);
        log.debug("found product: {}", product);
        ProductDto productDto = productToProductDtoConverter.convert(product);
        return productDto;
    }

    @Override
    public ProductDto addProduct(ProductDto productDtoToSave) {
        Product productToSave = productDtoToProductConverter.convert(productDtoToSave);
        Product savedProduct = productService.save(productToSave);
        log.debug("created product: {}", savedProduct);
        ProductDto productDto = productToProductDtoConverter.convert(savedProduct);
        return productDto;
    }

    @Override
    public void deleteProduct(Integer id) {
        boolean isDeleted = productService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDtoToUpdate) {
        Product productToUpdate = productDtoToProductConverter.convert(productDtoToUpdate);
        Product updatedProduct = productService.update(productToUpdate);
        log.debug("updated productDtoToUpdate: {}", updatedProduct);
        ProductDto productDto = productToProductDtoConverter.convert(updatedProduct);
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productService.getAll();
        log.trace("Get all products: {}", productList);
        List<ProductDto> productDtoList = productList.stream()
                .map(productToProductDtoConverter::convert)
                .collect(Collectors.toList());
        return productDtoList;
    }
}