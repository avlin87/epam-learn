package com.epam.liadov.service.stub;

import com.epam.liadov.domain.Supplier;
import com.epam.liadov.service.SupplierService;
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

/**
 * SupplierRepository - class for stubbed operations of Supplier class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class SupplierServiceStub implements SupplierService {

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
    public Supplier save(Supplier supplier) {
        log.debug("Supplier sent to DataBase successfully: {}", supplier);
        return supplier;
    }

    @Override
    public Supplier update(@NonNull Supplier supplier) {
        log.debug("object updated: {}", supplier);
        return supplier;
    }

    @Override
    public Supplier find(int primaryKey) {
        Supplier supplier = getSupplier();
        log.debug("Found supplier = {}", supplier);
        return supplier;

    }

    @Override
    public boolean delete(int primaryKey) {
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
