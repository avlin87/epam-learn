package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.service.CustomerService;
import com.epam.liadov.service.OrderService;
import com.epam.liadov.service.ProductService;
import com.epam.liadov.service.SupplierService;
import com.epam.liadov.service.impl.CustomerServiceImpl;
import com.epam.liadov.service.impl.OrderServiceImpl;
import com.epam.liadov.service.impl.ProductServiceImpl;
import com.epam.liadov.service.impl.SupplierServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * CrudServiceMain - class initialization of Spring Context
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CrudServiceMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.liadov");
        demonstrateCustomerOperations(context);
        demonstrateOrderOperations(context);
        demonstrateProductOperations(context);
        demonstrateSupplierOperations(context);
    }

    private static void demonstrateCustomerOperations(ApplicationContext context) {
        CustomerService customerService = context.getBean(CustomerServiceImpl.class);
        Customer customerFound = customerService.find(1);
        log.debug("found customer: {}", customerFound);

        var customerToCreate = new Customer();
        customerToCreate.setCustomerName("Customer " + String.valueOf(System.currentTimeMillis()).substring(4));
        customerToCreate.setPhone(customerFound.getPhone());

        customerToCreate = customerService.save(customerToCreate);
        log.debug("Created customer: {}", customerToCreate);

        var customerToUpdate = customerToCreate;
        customerToUpdate.setPhone("999-999-999");
        customerToUpdate = customerService.update(customerToUpdate);
        log.debug("Updated customer: {}", customerToUpdate);

        List<Customer> customerList = customerService.getAll();
        log.debug("customer getAll: {}", customerList);

        var customerToDelete = customerList.get(customerList.size() - 2);
        boolean deleteResult = customerService.delete(customerToDelete);
        log.debug("customer deleted: {}", deleteResult);
    }

    private static void demonstrateOrderOperations(ApplicationContext context) {
        OrderService orderService = context.getBean(OrderServiceImpl.class);
        Order orderToFind = orderService.find(1);
        log.debug("found order: {}", orderToFind);

        var orderToCreate = new Order();
        orderToCreate.setCustomerId(orderToFind.getCustomerId());
        orderToCreate.setOrderDate(orderToFind.getOrderDate());
        orderToCreate.setTotalAmount(orderToFind.getTotalAmount());
        orderToCreate.setProductId(new ArrayList<>(orderToFind.getProductId()));
        orderToCreate.setOrderNumber(String.valueOf(System.currentTimeMillis()).substring(9));
        orderToCreate = orderService.save(orderToCreate);
        log.debug("order saved: {}", orderToCreate);

        var orderToUpdate = orderToCreate;
        orderToUpdate.setTotalAmount(BigDecimal.TEN);
        orderToUpdate = orderService.update(orderToUpdate);
        log.debug("order updated: {}", orderToUpdate);

        List<Order> orderList = orderService.getAll();
        log.debug("order getAll: {}", orderList);

        var orderToDelete = orderList.get(orderList.size() - 2);
        boolean deleteResult = orderService.delete(orderToDelete);
        log.debug("order deleted: {}", deleteResult);
    }

    private static void demonstrateProductOperations(ApplicationContext context) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product productFound = productService.find(1);
        log.debug("found product: {}", productFound);

        var productToCreate = new Product();
        productToCreate.setProductName("Product " + String.valueOf(System.currentTimeMillis()).substring(4));
        productToCreate.setUnitPrice(productFound.getUnitPrice());
        productToCreate.setSupplierId(productFound.getSupplierId());

        productToCreate = productService.save(productToCreate);
        log.debug("Created product: {}", productToCreate);

        var productToUpdate = productToCreate;
        productToUpdate.setUnitPrice(BigDecimal.ONE);
        productToUpdate = productService.update(productToUpdate);
        log.debug("Updated product: {}", productToUpdate);

        List<Product> productList = productService.getAll();
        log.debug("product getAll: {}", productList);

        var productToDelete = productList.get(productList.size() - 2);
        boolean deleteResult = productService.delete(productToDelete);
        log.debug("product deleted: {}", deleteResult);
    }

    private static void demonstrateSupplierOperations(ApplicationContext context) {
        SupplierService supplierService = context.getBean(SupplierServiceImpl.class);
        Supplier supplierFound = supplierService.find(1);
        log.debug("found supplier: {}", supplierFound);

        var supplierToCreate = new Supplier();
        supplierToCreate.setCompanyName("Supplier " + String.valueOf(System.currentTimeMillis()).substring(4));
        supplierToCreate.setPhone(supplierFound.getPhone());

        supplierToCreate = supplierService.save(supplierToCreate);
        log.debug("Created supplier: {}", supplierToCreate);

        var supplierToUpdate = supplierToCreate;
        supplierToUpdate.setPhone("999-999-999");
        supplierToUpdate = supplierService.update(supplierToUpdate);
        log.debug("Updated supplier: {}", supplierToUpdate);

        List<Supplier> supplierList = supplierService.getAll();
        log.debug("supplier getAll: {}", supplierList);

        var supplierToDelete = new Supplier();
        supplierToDelete.setCompanyName("Supplier " + String.valueOf(System.currentTimeMillis()).substring(5));
        supplierToDelete.setPhone(supplierFound.getPhone());
        supplierToDelete = supplierService.save(supplierToDelete);
        boolean deleteResult = supplierService.delete(supplierToDelete);
        log.debug("supplier deleted: {}", deleteResult);
    }

}