package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.OrderDtoToOrderConverter;
import com.epam.liadov.converter.OrderToOrderDtoConverter;
import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.dto.OrderDto;
import com.epam.liadov.resource.OrderResource;
import com.epam.liadov.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderResourceImpl - class for RestController implementation of OrderResource interface
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderResourceImpl implements OrderResource {

    private final OrderService orderService;
    private final OrderToOrderDtoConverter toOrderDtoConverter;
    private final OrderDtoToOrderConverter toOrderConverter;

    @Override
    public OrderDto getOrder(Integer id) {
        Order order = orderService.find(id);
        log.debug("found order: {}", order);
        OrderDto orderDto = toOrderDtoConverter.convert(order);
        return orderDto;
    }

    @Override
    public OrderDto addOrder(OrderDto orderDtoToSave) {
        Order orderToSave = toOrderConverter.convert(orderDtoToSave);
        Order savedOrder = orderService.save(orderToSave);
        log.debug("created orderDtoToSave: {}", savedOrder);
        OrderDto orderDto = toOrderDtoConverter.convert(savedOrder);
        return orderDto;
    }

    @Override
    public void deleteOrder(Integer id) {
        boolean isDeleted = orderService.delete(id);
        log.debug("Entity removed: {}", isDeleted);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDtoToUpdate) {
        Order orderToUpdate = toOrderConverter.convert(orderDtoToUpdate);
        Order updatedOrder = orderService.update(orderToUpdate);
        log.debug("updated orderDtoToUpdate: {}", updatedOrder);
        OrderDto orderDto = toOrderDtoConverter.convert(updatedOrder);
        return orderDto;
    }

    @Override
    public List<OrderDto> getByCustomerId(Integer customerId) {
        List<Order> orderList = orderService.findByCustomerId(customerId);
        log.trace("Get orders by Customer Id: {}", orderList);
        List<OrderDto> orderDtoList = orderList.stream()
                .map(toOrderDtoConverter::convert)
                .collect(Collectors.toList());
        return orderDtoList;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orderList = orderService.getAll();
        log.trace("Get all orders: {}", orderList);
        List<OrderDto> orderDtoList = orderList.stream()
                .map(toOrderDtoConverter::convert)
                .collect(Collectors.toList());
        return orderDtoList;
    }
}