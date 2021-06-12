package com.epam.liadov.service.stub;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.service.CustomerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * CustomerServiceStub - class for stubbed CRUD operations of Customer class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class CustomerServiceStub implements CustomerService {

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
    public Customer save(Customer customer) {
        log.trace("customer sent to DB: {}", customer);
        return customer;
    }

    @Override
    public Customer update(@NonNull Customer customer) {
        log.debug("object updated: {}", customer);
        return customer;
    }

    @Override
    public Customer find(int customerId) {
        Customer customer = getCustomer();
        log.debug("Found customer = {}", customer);
        return customer;
    }

    @Override
    public boolean delete(int customerId) {
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
