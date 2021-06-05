package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import com.epam.liadov.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        Optional<Order> optionalOrder = orderRepository.save(order);
        boolean saveResult = optionalOrder.isPresent();
        if (saveResult) {
            boolean saveOrderProduct = true;//orderProductRepository.saveId(order.getOrderID(), order.getProducts());
            log.trace("OrderProduct updated: {}", saveOrderProduct);
            if (!saveOrderProduct) {
                delete(order);
                saveResult = false;
            }
        }
        log.trace("Order updated: {}", saveResult);
        if (saveResult) {
            order = optionalOrder.get();
            return order;
        }
        return new Order();
    }

    @Override
    public Order update(Order order) {
        Optional<Order> optionalOrder = orderRepository.update(order);
        boolean updateResult = optionalOrder.isPresent();
        if (updateResult) {
            //updateResult = orderProductRepository.updateId(order.getOrderID(), order.getProductId());
        }
        log.trace("Order updated: {}", updateResult);
        if (updateResult) {
            order = optionalOrder.get();
            return order;
        }
        return new Order();
    }

    @Override
    public Order find(int primaryKey) {
        Optional<Order> optionalOrder = orderRepository.find(primaryKey);
        boolean findResult = optionalOrder.isPresent();
        log.trace("Order found: {}", findResult);
        if (findResult) {
            Order order = optionalOrder.get();
            return order;
        }
        return new Order();
    }

    @Override
    public List<Order> findByCustomerId(int customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
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
