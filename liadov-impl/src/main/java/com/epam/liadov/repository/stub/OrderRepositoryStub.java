package com.epam.liadov.repository.stub;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 * OrderRepositoryImpl - class for stubbed operations of Order class.
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Profile("local")
public class OrderRepositoryStub implements OrderRepository {

    private final MessageSource messageSource;
    private final Locale locale;

    private int orderId;
    private int customerId;
    private String orderNumber;
    private BigDecimal orderTotalAmount;


    @PostConstruct
    public void init() {
        orderId = Integer.parseInt(Objects.requireNonNull(messageSource.getMessage("order.id", new Object[]{"1"}, "1", locale)));
        customerId = Integer.parseInt(Objects.requireNonNull(messageSource.getMessage("customer.id", new Object[]{"11"}, "11", locale)));
        orderNumber = messageSource.getMessage("order.number", new Object[]{"11"}, "155", locale);
        orderTotalAmount = BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(messageSource.getMessage("order.totalAmount", new Object[]{"11.11"}, "11.11", locale))));
    }

    @Override
    public Optional<Order> save(Order order) {
        log.debug("Order sent to DataBase successfully: {}", order);
        return Optional.ofNullable(order);
    }

    @Override
    public Optional<Order> update(@NonNull Order order) {
        log.debug("object updated: {}", order);
        return Optional.ofNullable(order);
    }

    @Override
    public Optional<Order> find(int primaryKey) {
        Order order = getOrder();
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
        return getOrders();
    }

    @Override
    public List<Order> getAll() {
        return getOrders();
    }

    private Order getOrder() {
        var order = new Order();
        order.setOrderID(orderId);
        order.setOrderNumber(orderNumber);
        order.setTotalAmount(orderTotalAmount);
        order.setCustomerId(customerId);
        order.setOrderDate(new Date());
        return order;
    }

    private List<Order> getOrders() {
        Order order1 = getOrder();
        Order order2 = getOrder();
        Order order3 = getOrder();
        List<Order> orderList = List.of(order1, order2, order3);
        log.trace("Found orders = {}", orderList);
        return orderList;
    }
}
