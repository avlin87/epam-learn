package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.ProductRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ProductRepositoryImpl - class for stubbed operations of Product class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("local")
public class ProductRepositoryStub implements ProductRepository {

    @Autowired
    EntityFactory factory;

    @Override
    public boolean save(Product product) {
        log.debug("{} sent to DataBase successfully", product);
        return true;
    }

    @Override
    public boolean update(@NonNull Product product) {
        log.debug("object updated: {}", product);
        return true;
    }

    @Override
    public Optional<Product> find(int primaryKey) {
        Supplier supplier = factory.generateTestSupplier();
        Product product = factory.generateTestProduct(supplier);
        product.setProductId(primaryKey);
        log.debug("Found product = {}", product);
        return Optional.ofNullable(product);
    }

    @Override
    public boolean delete(@NonNull Product product) {
        log.debug("object removed successfully");
        return true;
    }

    @Override
    public List<Product> getAll() {
        Supplier supplier = factory.generateTestSupplier();
        Product product1 = factory.generateTestProduct(supplier);
        Product product2 = factory.generateTestProduct(supplier);
        Product product3 = factory.generateTestProduct(supplier);
        List<Product> productList = List.of(product1, product2, product3);
        log.trace("Found products = {}", productList);
        return productList;
    }
}
