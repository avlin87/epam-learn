package com.epam.liadov.webservlet;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.service.SupplierService;
import com.epam.liadov.service.impl.SupplierServiceImpl;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
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
 * SupplierServlet - class for Json representation on Supplier class
 */

@Slf4j
public class SupplierServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private static SupplierService supplierService = new SupplierServiceImpl();
    private final String CONTENT_TYPE = "application/json";
    private final String CHARACTER_ENCODING = "UTF-8";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("Get parameters = {}", requestParameters);

        String supplierJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Supplier supplier = supplierService.find(key);
                    supplierJson = gson.toJson(supplier);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        } else if (parameterMap.isEmpty()) {
            List<Supplier> supplierList = supplierService.getAll();
            supplierJson = gson.toJson(supplierList, List.class);
        }
        printResponse(response, supplierJson);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var supplier = new Supplier();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("Post parameters: {}", requestParameters);

        supplier.setPhone(request.getParameter("phone"));
        supplier.setCompanyName(request.getParameter("companyName"));
        supplier = supplierService.save(supplier);

        String supplierJson = gson.toJson(supplier);
        printResponse(response, supplierJson);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var supplier = new Supplier();
        int key = getKey(request, supplier);
        if (key > 0) {
            supplier.setPhone(request.getParameter("phone"));
            supplier.setCompanyName(request.getParameter("companyName"));
            boolean updateResult = supplierService.update(supplier);
            log.trace("Supplier updated: {}", updateResult);
        }
        String supplierJson = gson.toJson(supplier);
        printResponse(response, supplierJson);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var supplier = new Supplier();
        int key = getKey(request, supplier);
        if (key > 0) {
            boolean removeResult = supplierService.delete(supplier);
            log.trace("Supplier updated: {}", removeResult);
        }
        String supplierJson = gson.toJson(supplier);
        printResponse(response, supplierJson);
    }

    private String getStringRequestParameters(Map<String, String[]> parameterMap) {
        return parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void printResponse(HttpServletResponse response, String supplierJson) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        out.print(supplierJson);
        out.flush();
    }

    private int getKey(HttpServletRequest request, Supplier supplier) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("PUT parameters: {}", requestParameters);

        int key = 0;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                supplier.setSupplierId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }
        return key;
    }
}