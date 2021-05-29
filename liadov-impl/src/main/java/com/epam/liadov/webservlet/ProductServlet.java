package com.epam.liadov.webservlet;

import com.epam.liadov.entity.Product;
import com.epam.liadov.service.ProductService;
import com.epam.liadov.service.impl.ProductServiceImpl;
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
 * ProductServlet - class for Json representation on Product class
 */
@Slf4j
public class ProductServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private static ProductService productService = new ProductServiceImpl();
    private final String CONTENT_TYPE = "application/json";
    private final String CHARACTER_ENCODING = "UTF-8";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("Get parameters = {}", requestParameters);

        String productJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Product product = productService.find(key);
                    productJson = gson.toJson(product);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        } else if (parameterMap.isEmpty()) {
            List<Product> productList = productService.getAll();
            productJson = gson.toJson(productList, List.class);
        }
        printResponse(response, productJson);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = parsProductFromJson(request);
        product = productService.save(product);
        String productJson = gson.toJson(product);
        printResponse(response, productJson);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = parsProductFromJson(request);
        boolean updateResult = productService.update(product);
        log.trace("Product updated: {}", updateResult);
        String productJson = gson.toJson(product);
        printResponse(response, productJson);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var product = new Product();
        int key = getKey(request, product);

        if (key > 0) {
            boolean removeResult = productService.delete(product);
            log.trace("Product updated: {}", removeResult);
        }
        String productJson = gson.toJson(product);
        printResponse(response, productJson);
    }

    private String getStringRequestParameters(Map<String, String[]> parameterMap) {
        return parameterMap
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " - " + Arrays.toString(entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void printResponse(HttpServletResponse response, String productJson) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        out.print(productJson);
        out.flush();
    }

    private Product parsProductFromJson(HttpServletRequest request) throws IOException {
        String requestBody = request.getReader()
                .lines()
                .collect(Collectors.joining("\n"));
        log.trace("body request: {}", requestBody);
        return gson.fromJson(requestBody, Product.class);
    }

    private int getKey(HttpServletRequest request, Product product) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String requestParameters = getStringRequestParameters(parameterMap);
        log.trace("PUT parameters: {}", requestParameters);

        int key = 0;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                product.setProductId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }
        return key;
    }
}