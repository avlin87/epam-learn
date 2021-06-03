package com.epam.liadov.service.impl;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderProductRepository;
import com.epam.liadov.repository.OrderRepository;
import com.epam.liadov.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public boolean save(Order order) {
        boolean saveOrderResult = orderRepository.save(order);
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
        boolean updateResult = orderRepository.update(order);
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
