package com.epam.liadov.service.impl;

import com.epam.liadov.domain.Order;
import com.epam.liadov.repository.OrderRepository;
import com.epam.liadov.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * OrderServiceImpl - Service for operations with Order repository
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!local")
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {

        Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.save(order));
        boolean saveResult = optionalOrder.isPresent();
        log.trace("Order created: {}", saveResult);
        if (saveResult) {
            order = optionalOrder.get();
            return order;
        }
        return new Order();
    }

    @Override
    public Order update(Order order) {
        int orderID = order.getOrderID();
        Optional<Order> optionalOrder = orderRepository.findById(orderID);
        if (optionalOrder.isPresent()) {
            optionalOrder = Optional.ofNullable(orderRepository.save(order));
        }
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            log.trace("Order updated: {}", order);
            return order;
        }
        log.trace("Order was not updated");
        return new Order();
    }

    @Override
    public Order find(int primaryKey) {
        Optional<Order> optionalOrder = orderRepository.findById(primaryKey);
        boolean findResult = optionalOrder.isPresent();
        log.trace("Order found: {}", findResult);
        if (findResult) {
            Order order = optionalOrder.get();
            return order;
        }
        return new Order();
    }

    @Override
    public boolean delete(int primaryKey) {
        boolean existsInDb = orderRepository.existsById(primaryKey);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            orderRepository.deleteById(primaryKey);
            boolean entityExistAfterRemove = orderRepository.existsById(primaryKey);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        return false;
    }

    @Override
    public List<Order> findByCustomerId(int customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

}
