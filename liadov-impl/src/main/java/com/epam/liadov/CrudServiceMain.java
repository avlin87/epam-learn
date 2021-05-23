package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import com.epam.liadov.repository.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

/**
 * CrudServiceMain - class initialization and demonstration of CRUD operations
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CrudServiceMain {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityFactory factory;

    public static void main(String[] args) {
        EntityManagerFactory entityPU = Persistence.createEntityManagerFactory("EntityPU");
        var crudController = new CrudServiceMain(entityPU);
        crudController.demonstrate();
    }

    public CrudServiceMain(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.factory = new EntityFactory();
    }

    /**
     * Method demonstrate creation, read, update and delete objects from database
     */
    private void demonstrate() {
        var customerRepository = new CustomerRepository(entityManagerFactory);
        var orderRepository = new OrderRepository(entityManagerFactory);
        var supplierRepository = new SupplierRepository(entityManagerFactory);
        var productRepository = new ProductRepository(entityManagerFactory);

        Customer customer = factory.generateTestCustomer();
        Optional<Customer> customerInDatabase = customerRepository.save(customer);
        if (customerInDatabase.isPresent()) {
            customer = customerInDatabase.get();
            log.trace("Customer created in DB = {}", customer);
        }

        Order order = factory.generateTestOrder(customer);
        Optional<Order> orderInDatabase = orderRepository.save(order);
        if (orderInDatabase.isPresent()) {
            order = orderInDatabase.get();
            log.trace("Order created in DB = {}", order);
        }

        Supplier supplier = factory.generateTestSupplier();
        Optional<Supplier> supplierInDatabase = supplierRepository.save(supplier);
        if (supplierInDatabase.isPresent()) {
            supplier = supplierInDatabase.get();
            log.trace("Supplier created in DB = {}", supplier);
        }

        Product product = factory.generateTestProduct(supplier);
        Optional<Product> productInDatabase = productRepository.save(product);
        if (productInDatabase.isPresent()) {
            product = productInDatabase.get();
            log.trace("Product created in DB = {}", product);
        }

        var orderProductRepository = new OrderProductRepository(entityManagerFactory);
        boolean isOrderProductSaved = orderProductRepository.save(order, product);
        log.trace("OrderProduct saved = {}", isOrderProductSaved);

        Customer customerUpdated = factory.updateCustomerObject(customer);
        boolean isCustomerUpdated = customerRepository.update(customerUpdated);
        log.trace("Customer updated = {}", isCustomerUpdated);

        Order orderUpdated = factory.updateOrderObject(order);
        boolean isOrderUpdated = orderRepository.update(orderUpdated);
        log.trace("Order updated = {}", isOrderUpdated);

        Supplier supplierUpdated = factory.updateSupplierObject(supplier);
        boolean isSupplierUpdated = supplierRepository.update(supplierUpdated);
        log.trace("Supplier updated = {}", isSupplierUpdated);

        Product productUpdated = factory.updateProductObject(product);
        boolean isProductUpdated = productRepository.update(productUpdated);
        log.trace("Product updated = {}", isProductUpdated);

        Optional<Customer> optionalCustomer = customerRepository.find(1);
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
            log.trace("Customer found: {}", customer);
        }

        Optional<Order> optionalOrder = orderRepository.find(2);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            log.trace("Order found: {}", order);
        }

        Optional<Supplier> optionalSupplier = supplierRepository.find(3);
        if (optionalSupplier.isPresent()) {
            supplier = optionalSupplier.get();
            log.trace("Supplier found: {}", supplier);
        }

        Optional<Product> optionalProduct = productRepository.find(4);
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
            log.trace("Product found: {}", product);
        }

        Optional<Customer> customerOptionalForDelete = customerRepository.find(1);
        if (customerOptionalForDelete.isPresent()) {
            Customer customerForDelete = customerOptionalForDelete.get();
            boolean isRemoved = customerRepository.delete(customerForDelete);
            log.trace("Customer removed = {}", isRemoved);
        }

        Optional<Supplier> supplierOptionalForDelete = supplierRepository.find(2);
        if (supplierOptionalForDelete.isPresent()) {
            Supplier supplierForDelete = supplierOptionalForDelete.get();
            boolean isRemoved = supplierRepository.delete(supplierForDelete);
            log.trace("Supplier removed = {}", isRemoved);
        }

        Optional<Order> orderOptionalForDelete = orderRepository.find(3);
        if (orderOptionalForDelete.isPresent()) {
            Order customerForDelete = orderOptionalForDelete.get();
            boolean isRemoved = orderRepository.delete(customerForDelete);
            log.trace("Order removed = {}", isRemoved);
        }

        Optional<Product> productOptionalForDelete = productRepository.find(4);
        if (productOptionalForDelete.isPresent()) {
            Product productForDelete = productOptionalForDelete.get();
            boolean isRemoved = productRepository.delete(productForDelete);
            log.trace("Product removed = {}", isRemoved);
        }
    }

}
