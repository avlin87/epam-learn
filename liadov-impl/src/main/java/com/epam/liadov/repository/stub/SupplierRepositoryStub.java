package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * SupplierRepository - class for stubbed operations of Supplier class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("local")
public class SupplierRepositoryStub implements SupplierRepository {

    @Autowired
    EntityFactory factory;

    @Override
    public boolean save(Supplier supplier) {
        log.debug("Supplier sent to DataBase successfully: {}", supplier);
        return true;
    }

    @Override
    public boolean update(@NonNull Supplier supplier) {
        log.debug("object updated: {}", supplier);
        return true;
    }

    @Override
    public Optional<Supplier> find(int primaryKey) {
        Supplier supplier = factory.generateTestSupplier();
        supplier.setSupplierId(primaryKey);
        log.debug("Found supplier = {}", supplier);
        return Optional.ofNullable(supplier);

    }

    @Override
    public boolean delete(@NonNull Supplier supplier) {
        log.debug("object removed successfully");
        return true;
    }

    @Override
    public List<Supplier> getAll() {
        Supplier supplier1 = factory.generateTestSupplier();
        Supplier supplier2 = factory.generateTestSupplier();
        Supplier supplier3 = factory.generateTestSupplier();
        List<Supplier> supplierList = List.of(supplier1, supplier2, supplier3);
        log.trace("Found suppliers = {}", supplierList);
        return supplierList;
    }
}
