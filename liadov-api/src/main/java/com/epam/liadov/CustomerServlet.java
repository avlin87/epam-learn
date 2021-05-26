package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CustomerServlet - class for Json representation on Customer class
 */
@Slf4j
public class CustomerServlet extends HttpServlet {

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

        String customerJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Customer customer = new CustomerRepository(entityPU).find(key).orElse(new Customer());
                    customerJson = gson.toJson(customer);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }

        } else if (parameterMap.isEmpty()) {
            List<Customer> customerList = new CustomerRepository(entityPU).getAll();
            customerJson = gson.toJson(customerList, List.class);
        }

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.print(customerJson);
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

        Customer customer = new Customer();

        customer.setCustomerName(request.getParameter("customerName"));
        customer.setPhone(request.getParameter("phone"));

        Optional<Customer> optionalCustomer = new CustomerRepository(entityPU).save(customer);

        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
            log.trace("Customer created successfully");
        } else {
            log.trace("Customer was not created");
        }

        String customerJson = gson.toJson(customer);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(customerJson);
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
        Customer customer = new Customer();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                customer.setCustomerId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            customer.setCustomerName(request.getParameter("customerName"));
            customer.setPhone(request.getParameter("phone"));
            boolean updateResult = new CustomerRepository(entityPU).update(customer);
            log.trace("Customer updated: {}", updateResult);
        }

        String customerJson = gson.toJson(customer);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(customerJson);
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
        Customer customer = new Customer();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                customer.setCustomerId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            boolean removeResult = new CustomerRepository(entityPU).delete(customer);
            log.trace("Customer updated: {}", removeResult);
        }

        String customerJson = gson.toJson(customer);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(customerJson);
        writer.flush();
    }
}