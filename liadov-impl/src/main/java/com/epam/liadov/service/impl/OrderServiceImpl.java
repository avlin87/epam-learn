package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import com.epam.liadov.service.OrderService;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

/**
 * OrderService
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");
    private final OrderRepository orderRepository = new OrderRepository(entityPU);

    @Override
    public Order save(Order order) {
        Order createdOrder = new Order();
        Optional<Order> optionalOrder = orderRepository.save(order);
        if (optionalOrder.isPresent()) {
            createdOrder = optionalOrder.get();
            log.trace("Order created successfully");
        } else {
            log.trace("Order was not created");
        }
        return createdOrder;
    }

    @Override
    public boolean update(Order order) {
        return orderRepository.update(order);
    }

    @Override
    public Order find(int primaryKey) {
        return orderRepository.find(primaryKey).orElse(new Order());
    }

    @Override
    public boolean delete(Order order) {
        return orderRepository.delete(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.getAll();
    }
}