package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CustomerRepositoryStub - class for stubbed CRUD operations of Customer class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("local")
public class CustomerRepositoryStub implements CustomerRepository {

    @Autowired
    private EntityFactory factory;

    @Override
    public boolean save(Customer customer) {
        log.trace("customer sent to DB: {}", customer);
        return true;
    }

    @Override
    public boolean update(@NonNull Customer customer) {
        log.debug("object updated: {}", customer);
        return true;
    }

    @Override
    public Optional<Customer> find(int primaryKey) {
        Customer customer = factory.generateTestCustomer();
        customer.setCustomerId(primaryKey);
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
        customerList.add(factory.generateTestCustomer());
        customerList.add(factory.generateTestCustomer());
        log.trace("Found customers = {}", customerList);
        return customerList;
    }
}
