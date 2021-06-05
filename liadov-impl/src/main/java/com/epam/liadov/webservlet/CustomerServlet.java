package com.epam.liadov.webservlet;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.service.CustomerService;
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
 * CustomerServlet - class for Json representation on Customer class
 */
@Slf4j
@Controller
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private final String CONTENT_TYPE = "application/json";
    private final String CHARACTER_ENCODING = "UTF-8";
    @Autowired
    private CustomerService customerService;
    private Gson gson = new Gson();
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

        String customerJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Customer customer = customerService.find(key);
                    customerJson = gson.toJson(customer);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        } else if (parameterMap.isEmpty()) {
            List<Customer> customerList = customerService.getAll();
            customerJson = gson.toJson(customerList, List.class);
        }
        printResponse(response, customerJson);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer customer = parsCustomerFromJson(request);
        customer = customerService.save(customer);
        log.trace("Customer saved: {}", customer);
        String customerJson = gson.toJson(customer);
        printResponse(response, customerJson);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Customer customer = parsCustomerFromJson(request);
        customer = customerService.update(customer);
        log.trace("Customer updated: {}", customer);
        String customerJson = gson.toJson(customer);
        printResponse(response, customerJson);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var customer = new Customer();
        int key = getKey(request, customer);
        if (key > 0) {
            boolean removeResult = customerService.delete(customer);
            log.trace("Customer updated: {}", removeResult);
        }
        String customerJson = gson.toJson(customer);
        printResponse(response, customerJson);
    }

    private String getStringRequestParameters(Map<String, String[]> parameterMap) {
        return parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void printResponse(HttpServletResponse response, String customerJson) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        out.print(customerJson);
        out.flush();
    }

    private Customer parsCustomerFromJson(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));
        log.trace("body request: {}", requestBody);
        return gson.fromJson(requestBody, Customer.class);
    }

    private int getKey(HttpServletRequest request, Customer customer) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("PUT parameters: {}", requestParameters);

        int key = 0;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                customer.setCustomerId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }
        return key;
    }
}