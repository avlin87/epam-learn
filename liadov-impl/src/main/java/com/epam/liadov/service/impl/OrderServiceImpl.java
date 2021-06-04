package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderProductRepository;
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
    private final OrderProductRepository orderProductRepository;

    @Override
    public boolean save(Order order) {
        Optional<Order> optionalOrder = orderRepository.save(order);
        boolean saveOrderResult = optionalOrder.isPresent();
        log.trace("Order created: {}", saveOrderResult);
        if (saveOrderResult) {
            boolean saveOrderProduct = orderProductRepository.saveId(order.getOrderID(), order.getProductId());
            log.trace("OrderProduct updated: {}", saveOrderProduct);
            if (!saveOrderProduct) {
                delete(order);
                saveOrderResult = false;
            }
        }
        return saveOrderResult;
    }

    @Override
    public boolean update(Order order) {
        Optional<Order> optionalOrder = orderRepository.update(order);
        boolean updateResult = optionalOrder.isPresent();
        if (updateResult) {
            updateResult = orderProductRepository.updateId(order.getOrderID(), order.getProductId());
        }
        return updateResult;
    }

    @Override
    public Order find(int primaryKey) {
        return orderRepository.find(primaryKey).orElse(new Order());
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
