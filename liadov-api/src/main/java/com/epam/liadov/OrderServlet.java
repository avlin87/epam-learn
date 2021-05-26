package com.epam.liadov;

import com.epam.liadov.entity.Order;
import com.epam.liadov.repository.OrderRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OrderServlet - class for Json representation on Order class
 */
@Slf4j
public class OrderServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private static final EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();

        String requestParameters = parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
        log.trace("Get parameters = {}", requestParameters);

        String orderJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Order order = new OrderRepository(entityPU).find(key).orElse(new Order());
                    orderJson = gson.toJson(order);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }

        } else if (parameterMap.isEmpty()) {
            List<Order> orderList = new OrderRepository(entityPU).getAll();
            orderJson = gson.toJson(orderList, List.class);
        }

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.print(orderJson);
        out.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
        log.trace("Post parameters: {}", requestParameters);

        Order order = new Order();

        order.setOrderNumber(request.getParameter("orderNumber"));
        order.setTotalAmount(BigDecimal.valueOf(Long.parseLong(request.getParameter("totalAmount"))));
        order.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        order.setOrderDate(new Date());

        Optional<Order> optionalOrder = new OrderRepository(entityPU).save(order);

        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            log.trace("Order created successfully");
        } else {
            log.trace("Order was not created");
        }

        String orderJson = gson.toJson(order);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(orderJson);
        writer.flush();
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
        log.trace("PUT parameters: {}", requestParameters);

        int key = 0;
        Order order = new Order();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                order.setOrderID(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            order.setOrderNumber(request.getParameter("orderNumber"));
            order.setTotalAmount(BigDecimal.valueOf(Long.parseLong(request.getParameter("totalAmount"))));
            order.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
            order.setOrderDate(new Date());
            boolean updateResult = new OrderRepository(entityPU).update(order);
            log.trace("Order updated: {}", updateResult);
        }

        String orderJson = gson.toJson(order);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(orderJson);
        writer.flush();
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
        log.trace("PUT parameters: {}", requestParameters);

        int key = 0;
        Order order = new Order();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                order.setOrderID(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            boolean removeResult = new OrderRepository(entityPU).delete(order);
            log.trace("Order updated: {}", removeResult);
        }

        String orderJson = gson.toJson(order);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(orderJson);
        writer.flush();
    }
}