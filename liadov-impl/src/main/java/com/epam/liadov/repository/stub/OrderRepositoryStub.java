package com.epam.liadov.repository.stub;

import com.epam.liadov.EntityFactory;
import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * OrderRepositoryImpl - class for stubbed operations of Order class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Repository
@Profile("local")
public class OrderRepositoryStub implements OrderRepository {

    @Autowired
    private EntityFactory factory;

    @Override
    public boolean save(Order order) {
        log.debug("Order sent to DataBase successfully: {}", order);
        return true;
    }

    @Override
    public boolean update(@NonNull Order order) {
        log.debug("object updated: {}", order);
        return true;
    }

    @Override
    public Optional<Order> find(int primaryKey) {
        Customer customer = factory.generateTestCustomer();
        Order order = factory.generateTestOrder(customer);
        order.setOrderID(primaryKey);
        log.debug("Found order = {}", order);
        return Optional.ofNullable(order);
    }

    @Override
    public boolean delete(@NonNull Order order) {
        log.debug("object removed successfully");
        return true;
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        Customer customer = factory.generateTestCustomer();
        customer.setCustomerId(customerId);
        return getOrders(customer);
    }

    @Override
    public List<Order> getAll() {
        Customer customer = factory.generateTestCustomer();
        return getOrders(customer);
    }

    private List<Order> getOrders(Customer customer) {
        Order order1 = factory.generateTestOrder(customer);
        Order order2 = factory.generateTestOrder(customer);
        Order order3 = factory.generateTestOrder(customer);
        List<Order> orderList = List.of(order1, order2, order3);
        log.trace("Found orders = {}", orderList);
        return orderList;
    }
}
