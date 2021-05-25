package com.epam.liadov;

import com.epam.liadov.entity.Product;
import com.epam.liadov.repository.ProductRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProductServlet extends HttpServlet {

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

        String productJson = "";
        int key;
        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                if (key > 0) {
                    Product product = new ProductRepository(entityPU).find(key).orElse(new Product());
                    productJson = gson.toJson(product);
                }
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }

        } else if (parameterMap.isEmpty()) {
            List<Product> productList = new ProductRepository(entityPU).getAll();
            productJson = gson.toJson(productList, List.class);
        }

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        out.print(productJson);
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

        Product product = new Product();

        product.setProductName(request.getParameter("productName"));
        product.setSupplierId(Integer.parseInt(request.getParameter("supplierId")));
        product.setUnitPrice(BigDecimal.valueOf(Long.parseLong(request.getParameter("unitPrice"))));
        product.setDiscontinued(Boolean.parseBoolean(request.getParameter("discontinued")));

        Optional<Product> optionalProduct = new ProductRepository(entityPU).save(product);

        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            log.trace("Product created successfully");
        } else {
            log.trace("Product was not created");
        }

        String productJson = gson.toJson(product);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(productJson);
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
        Product product = new Product();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                product.setProductId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            product.setProductName(request.getParameter("productName"));
            product.setSupplierId(Integer.parseInt(request.getParameter("supplierId")));
            product.setUnitPrice(BigDecimal.valueOf(Long.parseLong(request.getParameter("unitPrice"))));
            product.setDiscontinued(Boolean.parseBoolean(request.getParameter("discontinued")));
            boolean updateResult = new ProductRepository(entityPU).update(product);
            log.trace("Product updated: {}", updateResult);
        }

        String productJson = gson.toJson(product);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(productJson);
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
        Product product = new Product();

        if (parameterMap.containsKey("id")) {
            try {
                key = Integer.parseInt(request.getParameter("id"));
                product.setProductId(key);
            } catch (NumberFormatException e) {
                log.trace("provided ID is invalid", e);
            }
        }

        if (key > 0) {
            boolean removeResult = new ProductRepository(entityPU).delete(product);
            log.trace("Product updated: {}", removeResult);
        }

        String productJson = gson.toJson(product);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        writer.print(productJson);
        writer.flush();
    }
}