package com.epam.liadov;

import com.epam.liadov.entity.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CrudController - class CRUD operations
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CrudController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityPU");

    /**
     * Method demonstrate creation, read, update and delete objects from database
     */
    public void demonstrate() {
        Customer customer = createCustomer();
        Order order = createOrder(customer);
        Supplier supplier = createSupplier();
        Product product = createProduct(supplier);
        updateOrderProduct(order, product);

        updateCustomer(customer);
        updateOrder(order);
        updateSupplier(supplier);
        updateProduct(product);

        customer = findCustomer();
        if (customer != null) {
            order = findOrder(customer);
        }
        supplier = findSupplier();
        if (supplier != null) {
            product = findProduct(supplier);
        }

        deleteCustomer();
        deleteSupplier();
    }

    private void deleteCustomer() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = entityManager.find(Customer.class, 1);

        boolean contains = entityManager.contains(customer);
        log.debug("object exist: {}", contains);
        if (contains) {
            entityManager.remove(customer);
        }
        transaction.commit();

        entityManager.close();
    }

    private void deleteSupplier() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Supplier supplier = entityManager.find(Supplier.class, 1);

        boolean contains = entityManager.contains(supplier);
        log.debug("object exist: {}", contains);
        if (contains) {
            entityManager.remove(supplier);
        }
        transaction.commit();

        entityManager.close();
    }

    private Product findProduct(@NonNull Supplier supplier) {
        EntityManager entityManager = emf.createEntityManager();
        int supplierId = supplier.getSupplierId();
        Product product = entityManager.find(Product.class, supplierId);
        entityManager.close();
        return product;
    }

    private Supplier findSupplier() {
        EntityManager entityManager = emf.createEntityManager();
        Supplier supplier = entityManager.find(Supplier.class, 1);
        entityManager.close();
        return supplier;
    }

    private Order findOrder(@NonNull Customer customer) {
        EntityManager entityManager = emf.createEntityManager();
        int customerId = customer.getCustomerId();
        Order order = entityManager.find(Order.class, customerId);
        entityManager.close();
        return order;
    }

    private Customer findCustomer() {
        EntityManager entityManager = emf.createEntityManager();
        Customer customer = entityManager.find(Customer.class, 1);
        entityManager.close();
        return customer;
    }

    private void updateProduct(Product product) {
        product.setUnitPrice(BigDecimal.valueOf(11.12));
        updateDatabase(product);
    }

    private void updateSupplier(Supplier supplier) {
        StringBuilder sb = new StringBuilder(supplier.getCompanyName());
        sb.insert(0, "Updated ");
        supplier.setCompanyName(sb.toString());
        updateDatabase(supplier);
    }

    private void updateOrder(Order order) {
        order.setTotalAmount(BigDecimal.valueOf(21.234));
        updateDatabase(order);
    }

    private void updateCustomer(Customer customer) {
        StringBuilder sb = new StringBuilder(customer.getCustomerName());
        sb.append(" Updated");
        customer.setCustomerName(sb.toString());
        updateDatabase(customer);
    }

    private Customer createCustomer() {
        long timeMillis = System.currentTimeMillis();
        String customerName = String.valueOf(timeMillis).substring(10, 13) + " Customer";
        String phone = String.valueOf(timeMillis).replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "+$1 ($2) $3-$4");
        Customer customer = new Customer(customerName, phone);

        createInDatabase(customer);

        log.debug("Customer created successfully: {}", customer);
        return customer;
    }


    private Order createOrder(Customer customer) {
        String orderNumber = String.valueOf(System.currentTimeMillis()).substring(3, 13);
        BigDecimal totalAmount = BigDecimal.valueOf(System.currentTimeMillis() / 1000000000.0);
        int customerId = customer.getCustomerId();
        Order order = new Order(orderNumber, customerId, new Date(), totalAmount);

        createInDatabase(order);

        log.debug("Order created successfully: {}", order);
        return order;
    }

    private Supplier createSupplier() {
        long timeMillis = System.currentTimeMillis();
        String companyName = String.valueOf(timeMillis).substring(10, 13) + " Company";
        String phone = String.valueOf(timeMillis).replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "+$1 ($2) $3-$4");
        Supplier supplier = new Supplier(companyName, phone);

        createInDatabase(supplier);

        log.debug("Supplier created successfully: {}", supplier);
        return supplier;
    }

    private Product createProduct(Supplier supplier) {
        long timeMillis = System.currentTimeMillis();
        String productName = String.valueOf(timeMillis).substring(10, 13) + " Product";
        BigDecimal unitPrice = BigDecimal.valueOf(System.currentTimeMillis() / 100000000000.0);
        int supplierId = supplier.getSupplierId();
        Product product = new Product(productName, supplierId, unitPrice, false);

        createInDatabase(product);

        log.debug("Product created successfully: {}", product);
        return product;
    }

    private void updateOrderProduct(Order order, Product product) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.createNativeQuery("insert into liadov.orderproduct (orderid, productid) values (?,?)")
                .setParameter(1, order.getOrderID())
                .setParameter(2, product.getProductId())
                .executeUpdate();
        transaction.commit();

        entityManager.close();
    }

    private void createInDatabase(CrudEntity crudEntity) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(crudEntity);
        transaction.commit();

        entityManager.close();
    }

    private void updateDatabase(CrudEntity crudEntity) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.merge(crudEntity);
        transaction.commit();

        entityManager.close();
    }
}
