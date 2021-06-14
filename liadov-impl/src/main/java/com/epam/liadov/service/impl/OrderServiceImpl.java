package com.epam.liadov.service.impl;

import com.epam.liadov.Logging;
import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.exception.BadRequestException;
import com.epam.liadov.exception.NoContentException;
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

    @Logging
    @Override
    public Order save(Order order) {

        Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.save(order));
        boolean saveResult = optionalOrder.isPresent();
        log.trace("Order created: {}", saveResult);
        if (saveResult) {
            order = optionalOrder.get();
            return order;
        }
        throw new BadRequestException("Order was not saved");
    }

    @Logging
    @Override
    public Order update(Order order) {
        int orderID = order.getOrderID();
        Optional<Order> optionalOrder = orderRepository.findById(orderID);
        if (optionalOrder.isPresent()) {
            optionalOrder = Optional.ofNullable(orderRepository.save(order));
            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                log.trace("Order updated: {}", order);
                return order;
            }
        }
        log.trace("Order was not updated");
        throw new NoContentException("Order does not exist");
    }

    @Logging
    @Override
    public Order find(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        boolean findResult = optionalOrder.isPresent();
        log.trace("Order found: {}", findResult);
        if (findResult) {
            Order order = optionalOrder.get();
            return order;
        }
        throw new NoContentException("Order does not exist");
    }

    @Logging
    @Override
    public boolean delete(int orderId) {
        boolean existsInDb = orderRepository.existsById(orderId);
        log.trace("Entity for removal exist in BD: {}", existsInDb);
        if (existsInDb) {
            orderRepository.deleteById(orderId);
            boolean entityExistAfterRemove = orderRepository.existsById(orderId);
            log.trace("Entity removed: {}", entityExistAfterRemove);
            return !entityExistAfterRemove;
        }
        throw new NoContentException("Order does not exist");
    }

    @Logging
    @Override
    public List<Order> findByCustomerId(int customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Logging
    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

}
