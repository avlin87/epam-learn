package com.epam.liadov.webservlet;

import com.epam.liadov.entity.Order;
import com.epam.liadov.service.OrderService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrderServlet - class for Json representation on Order class
 */
@Slf4j
@Controller
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private final String CONTENT_TYPE = "application/json";
    private final String CHARACTER_ENCODING = "UTF-8";
    @Autowired
    private OrderService orderService;
    private WebApplicationContext springContext;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("Get parameters = {}", requestParameters);

        String orderJson = "";
        int key;
        if (parameterMap.containsKey("customerId")) {
            try {
                key = Integer.parseInt(request.getParameter("customerId"));
                if (key > 0) {
                    List<Order> orderList = orderService.findByCustomerId(key);
                    orderJson = gson.toJson(orderList);
                }
            } catch (NumberFormatException e) {
                log.trace("provided customerId is invalid", e);
            }
        } else if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Order order = orderService.find(key);
                    orderJson = gson.toJson(order);
                }
            } catch (NumberFormatException e) {
                log.trace("provided id is invalid", e);
            }
        } else if (parameterMap.isEmpty()) {
            List<Order> orderList = orderService.getAll();
            orderJson = gson.toJson(orderList, List.class);
        }
        printResponse(response, orderJson);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = parsOrderFromJson(request);
        boolean saveResult = orderService.save(order);
        log.trace("Order saved: {}", saveResult);
        String orderJson = gson.toJson(order);
        printResponse(response, orderJson);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Order order = parsOrderFromJson(request);
        boolean updateResult = orderService.update(order);
        log.trace("Order updated: {}", updateResult);
        String orderJson = gson.toJson(order);
        printResponse(response, orderJson);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private Order parsOrderFromJson(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));
        log.trace("body request: {}", requestBody);
        Order order = gson.fromJson(requestBody, Order.class);
        log.trace("order = {}", order);
        return order;
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