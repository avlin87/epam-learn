package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.CustomerRepository;
import com.epam.liadov.repository.OrderRepository;
import com.epam.liadov.repository.ProductRepository;
import com.epam.liadov.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

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
        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
        Optional<Customer> customerOptional = customerRepository.find(1);
        var customer = new Customer();
        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
            log.debug("found customer: {}", customer);
        }

        Optional<Customer> optionalCustomer = customerRepository.save(customer);
        boolean saveResult = optionalCustomer.isPresent();
        log.debug("customer saved: {}", saveResult);

        boolean deleteResult = customerRepository.delete(customer);
        log.debug("customer deleted: {}", deleteResult);

        Optional<Customer> optionalCustomer1 = customerRepository.update(customer);
        boolean updateResult = optionalCustomer1.isPresent();
        log.debug("customer updated: {}", updateResult);

        List<Customer> customerList = customerRepository.getAll();
        log.debug("customer getAll: {}", customerList);
    }

    private static void demonstrateOrderOperations(ApplicationContext context) {
        OrderRepository orderRepository = context.getBean(OrderRepository.class);
        Optional<Order> orderOptional = orderRepository.find(2);
        var order = new Order();
        if (orderOptional.isPresent()) {
            order = orderOptional.get();
        }
        log.debug("found order: {}", order);

        Optional<Order> optionalOrder = orderRepository.save(order);
        boolean saveResult = optionalOrder.isPresent();
        log.debug("order saved: {}", saveResult);

        boolean deleteResult = orderRepository.delete(order);
        log.debug("order deleted: {}", deleteResult);

        Optional<Order> optionalOrder1 = orderRepository.update(order);
        boolean updateResult = optionalOrder1.isPresent();
        log.debug("order updated: {}", updateResult);

        List<Order> orderList = orderRepository.getAll();
        log.debug("order getAll: {}", orderList);
    }

    private static void demonstrateProductOperations(ApplicationContext context) {
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        Optional<Product> productOptional = productRepository.find(9);
        var product = new Product();
        if (productOptional.isPresent()) {
            product = productOptional.get();
        }
        log.debug("found product: {}", product);

        Optional<Product> optionalProduct = productRepository.save(product);
        boolean saveResult = optionalProduct.isPresent();
        log.debug("product saved: {}", saveResult);

        boolean deleteResult = productRepository.delete(product);
        log.debug("product deleted: {}", deleteResult);

        Optional<Product> optionalProduct1 = productRepository.update(product);
        boolean updateResult = optionalProduct1.isPresent();
        log.debug("product updated: {}", updateResult);

        List<Product> productList = productRepository.getAll();
        log.debug("product getAll: {}", productList);
    }

    private static void demonstrateSupplierOperations(ApplicationContext context) {
        SupplierRepository supplierRepository = context.getBean(SupplierRepository.class);
        Optional<Supplier> supplierOptional = supplierRepository.find(7);
        var supplier = new Supplier();
        if (supplierOptional.isPresent()) {
            supplier = supplierOptional.get();
        }
        log.debug("found supplier: {}", supplier);

        Optional<Supplier> optionalSupplier = supplierRepository.save(supplier);
        boolean saveResult = optionalSupplier.isPresent();
        log.debug("supplier saved: {}", saveResult);

        boolean deleteResult = supplierRepository.delete(supplier);
        log.debug("supplier deleted: {}", deleteResult);

        Optional<Supplier> optionalSupplier1 = supplierRepository.update(supplier);
        boolean updateResult = optionalSupplier1.isPresent();
        log.debug("supplier updated: {}", updateResult);

        List<Supplier> supplierList = supplierRepository.getAll();
        log.debug("supplier getAll: {}", supplierList);
    }

}