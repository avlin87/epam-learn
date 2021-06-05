package com.epam.liadov.webservlet;

import com.epam.liadov.entity.Supplier;
import com.epam.liadov.service.SupplierService;
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
 * SupplierServlet - class for Json representation on Supplier class
 */
@Slf4j
@Controller
@WebServlet("/supplier")
public class SupplierServlet extends HttpServlet {

    private final String CONTENT_TYPE = "application/json";
    private final String CHARACTER_ENCODING = "UTF-8";
    @Autowired
    private SupplierService supplierService;
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
        Supplier supplier = parsSupplierFromJson(request);
        supplier = supplierService.save(supplier);
        log.trace("Supplier updated: {}", supplier);
        String supplierJson = gson.toJson(supplier);
        printResponse(response, supplierJson);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Supplier supplier = parsSupplierFromJson(request);
        supplier = supplierService.update(supplier);
        log.trace("Supplier updated: {}", supplier);
        String supplierJson = gson.toJson(supplier);
        printResponse(response, supplierJson);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private Supplier parsSupplierFromJson(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));
        log.trace("body request: {}", requestBody);
        return gson.fromJson(requestBody, Supplier.class);
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