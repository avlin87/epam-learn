package com.epam.liadov.repository.stub;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * SupplierRepository - class for stubbed operations of Supplier class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class SupplierRepositoryStub implements SupplierRepository {

    private final MessageSource messageSource;
    private final Locale locale;

    private int supplierId;
    private String supplierCompanyName;
    private String supplierPhone;

    @PostConstruct
    public void init() {
        supplierId = Integer.parseInt(Objects.requireNonNull(messageSource.getMessage("supplier.id", new Object[]{"4"}, "4", locale)));
        supplierCompanyName = messageSource.getMessage("supplier.companyName", new Object[]{"ProductName"}, "Product default", locale);
        supplierPhone = messageSource.getMessage("supplier.phone", new Object[]{"11-11-11"}, "44-44-44", locale);
    }

    @Override
    public Optional<Supplier> save(Supplier supplier) {
        log.debug("Supplier sent to DataBase successfully: {}", supplier);
        return Optional.ofNullable(supplier);
    }

    @Override
    public Optional<Supplier> update(@NonNull Supplier supplier) {
        log.debug("object updated: {}", supplier);
        return Optional.ofNullable(supplier);
    }

    @Override
    public Optional<Supplier> find(int primaryKey) {
        Supplier supplier = getSupplier();

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
        Supplier supplier1 = getSupplier();
        Supplier supplier2 = getSupplier();
        Supplier supplier3 = getSupplier();
        List<Supplier> supplierList = List.of(supplier1, supplier2, supplier3);
        log.trace("Found suppliers = {}", supplierList);
        return supplierList;
    }

    private Supplier getSupplier() {
        var supplier = new Supplier();
        supplier.setSupplierId(supplierId);
        supplier.setCompanyName(supplierCompanyName);
        supplier.setPhone(supplierPhone);
        return supplier;
    }
}
