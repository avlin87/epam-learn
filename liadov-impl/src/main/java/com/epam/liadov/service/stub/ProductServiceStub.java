package com.epam.liadov.service.stub;

import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * ProductRepositoryImpl - class for stubbed operations of Product class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class ProductServiceStub implements ProductService {

    private final MessageSource messageSource;
    private final Locale locale;

    private int productId;
    private int supplierId;
    private boolean discontinued;
    private String productName;
    private BigDecimal productUnitPrice;

    @PostConstruct
    public void init() {
        productId = Integer.parseInt(Objects.requireNonNull(messageSource.getMessage("product.id", new Object[]{"1"}, "1", locale)));
        supplierId = Integer.parseInt(Objects.requireNonNull(messageSource.getMessage("supplier.id", new Object[]{"4"}, "4", locale)));
        discontinued = Boolean.parseBoolean(messageSource.getMessage("product.discontinued", new Object[]{"false"}, "false", locale));
        productName = messageSource.getMessage("product.name", new Object[]{"ProductName"}, "Product default", locale);
        productUnitPrice = BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(messageSource.getMessage("product.unitPrice", new Object[]{"11.12"}, "11.12", locale))));
    }

    @Override
    public Product save(Product product) {
        log.debug("{} sent to DataBase successfully", product);
        return product;
    }

    @Override
    public Product update(@NonNull Product product) {
        log.debug("object updated: {}", product);
        return product;
    }

    @Override
    public Product find(int productId) {
        Product product = getProduct();
        log.debug("Found product = {}", product);
        return product;
    }

    @Override
    public boolean delete(int productId) {
        log.debug("object removed successfully");
        return true;
    }

    @Override
    public List<Product> getAll() {
        Product product1 = getProduct();
        Product product2 = getProduct();
        Product product3 = getProduct();
        List<Product> productList = List.of(product1, product2, product3);
        log.trace("Found products = {}", productList);
        return productList;
    }

    private Product getProduct() {
        var product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setSupplierId(supplierId);
        product.setUnitPrice(productUnitPrice);
        product.setDiscontinued(discontinued);
        return product;
    }
}