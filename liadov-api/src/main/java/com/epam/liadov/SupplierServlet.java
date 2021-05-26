package com.epam.liadov;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.SupplierRepository;
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
 * SupplierServlet - class for Json representation on Supplier class
 */

@Slf4j
public class SupplierServlet extends HttpServlet {

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

        String supplierJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Supplier supplier = new SupplierRepository(entityPU).find(key).orElse(new Supplier());
                    supplierJson = gson.toJson(supplier);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }

        } else if (parameterMap.isEmpty()) {
            List<Supplier> supplierList = new SupplierRepository(entityPU).getAll();
            supplierJson = gson.toJson(supplierList, List.class);
        }

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.print(supplierJson);
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

        Supplier supplier = new Supplier();

        supplier.setPhone(request.getParameter("phone"));
        supplier.setCompanyName(request.getParameter("companyName"));

        Optional<Supplier> optionalSupplier = new SupplierRepository(entityPU).save(supplier);

        if (optionalSupplier.isPresent()) {
            supplier = optionalSupplier.get();
            log.trace("Supplier created successfully");
        } else {
            log.trace("Supplier was not created");
        }

        String supplierJson = gson.toJson(supplier);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(supplierJson);
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
        Supplier supplier = new Supplier();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                supplier.setSupplierId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            supplier.setPhone(request.getParameter("phone"));
            supplier.setCompanyName(request.getParameter("companyName"));
            boolean updateResult = new SupplierRepository(entityPU).update(supplier);
            log.trace("Supplier updated: {}", updateResult);
        }

        String supplierJson = gson.toJson(supplier);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(supplierJson);
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
        Supplier supplier = new Supplier();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                supplier.setSupplierId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            boolean removeResult = new SupplierRepository(entityPU).delete(supplier);
            log.trace("Supplier updated: {}", removeResult);
        }

        String supplierJson = gson.toJson(supplier);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(supplierJson);
        writer.flush();
    }
}