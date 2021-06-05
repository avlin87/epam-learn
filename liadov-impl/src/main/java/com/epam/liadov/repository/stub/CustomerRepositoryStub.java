package com.epam.liadov.repository.stub;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * CustomerRepositoryStub - class for stubbed CRUD operations of Customer class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class CustomerRepositoryStub implements CustomerRepository {

    private final MessageSource messageSource;
    private final Locale locale;

    private String customerName;
    private String customerPhone;
    private int customerId;

    @PostConstruct
    public void init() {
        customerName = messageSource.getMessage("customer.name", new Object[]{System.currentTimeMillis()}, "155 Customer", locale);
        customerId = Integer.parseInt(Objects.requireNonNull(messageSource.getMessage("customer.id", new Object[]{"1"}, "1", locale)));
        customerPhone = messageSource.getMessage("customer.phone", new Object[]{System.currentTimeMillis()}, "+1 (622) 741-612155)", locale);
    }

    @Override
    public Optional<Customer> save(Customer customer) {
        log.trace("customer sent to DB: {}", customer);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> update(@NonNull Customer customer) {
        log.debug("object updated: {}", customer);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> find(int primaryKey) {
        Customer customer = getCustomer();
        log.debug("Found customer = {}", customer);
        return Optional.ofNullable(customer);
    }

    @Override
    public boolean delete(@NonNull Customer customer) {
        log.debug("object removed successfully");
        return true;
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer1 = getCustomer();
        Customer customer2 = getCustomer();

        customerList.add(customer1);
        customerList.add(customer2);
        log.trace("Found customers = {}", customerList);
        return customerList;
    }

    private Customer getCustomer() {
        var customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        customer.setPhone(customerPhone);
        return customer;
    }
}
