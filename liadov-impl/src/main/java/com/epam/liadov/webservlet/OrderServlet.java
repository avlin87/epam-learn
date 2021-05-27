package com.epam.liadov.webservlet;

import com.epam.liadov.entity.Order;
import com.epam.liadov.service.OrderService;
import com.epam.liadov.service.impl.OrderServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrderServlet - class for Json representation on Order class
 */
@Slf4j
public class OrderServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private final OrderService orderService = new OrderServiceImpl();
    private final String CONTENT_TYPE = "application/json";
    private final String CHARACTER_ENCODING = "UTF-8";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("Get parameters = {}", requestParameters);

        String orderJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Order order = orderService.find(key);
                    orderJson = gson.toJson(order);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        } else if (parameterMap.isEmpty()) {
            List<Order> orderList = orderService.getAll();
            orderJson = gson.toJson(orderList, List.class);
        }
        printResponse(response, orderJson);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var order = new Order();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("Post parameters: {}", requestParameters);

        order.setOrderNumber(request.getParameter("orderNumber"));
        order.setTotalAmount(BigDecimal.valueOf(Long.parseLong(request.getParameter("totalAmount"))));
        order.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        order.setOrderDate(new Date());
        order = orderService.save(order);
        String orderJson = gson.toJson(order);
        printResponse(response, orderJson);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var order = new Order();
        int key = getKey(request, order);
        if (key > 0) {
            order.setOrderNumber(request.getParameter("orderNumber"));
            order.setTotalAmount(BigDecimal.valueOf(Long.parseLong(request.getParameter("totalAmount"))));
            order.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
            order.setOrderDate(new Date());
            boolean updateResult = orderService.update(order);
            log.trace("Order updated: {}", updateResult);
        }
        String orderJson = gson.toJson(order);
        printResponse(response, orderJson);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var order = new Order();
        int key = getKey(request, order);
        if (key > 0) {
            boolean removeResult = orderService.delete(order);
            log.trace("Order updated: {}", removeResult);
        }
        String orderJson = gson.toJson(order);
        printResponse(response, orderJson);
    }

    private String getStringRequestParameters(Map<String, String[]> parameterMap) {
        return parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void printResponse(HttpServletResponse response, String orderJson) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        out.print(orderJson);
        out.flush();
    }

    private int getKey(HttpServletRequest request, Order order) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("PUT parameters: {}", requestParameters);

        int key = 0;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                order.setOrderID(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }
        return key;
    }
}