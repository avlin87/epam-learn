package com.epam.liadov;

import com.epam.liadov.entity.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * CrudService - class CRUD operations
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CrudService {

    private final EntityManagerFactory entityManagerFactory;

    public CrudService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Method demonstrate creation, read, update and delete objects from database
     */
    public void demonstrate() {
        Customer customer = generateTestCustomer();
        Optional<Customer> customerInDatabase = createEntityInDatabase(customer);
        if (customerInDatabase.isPresent()) {
            customer = customerInDatabase.get();
        }

        Order order = generateTestOrder(customer);
        Optional<Order> orderInDatabase = createEntityInDatabase(order);
        if (orderInDatabase.isPresent()) {
            order = orderInDatabase.get();
        }

        Supplier supplier = generateTestSupplier();
        Optional<Supplier> supplierInDatabase = createEntityInDatabase(supplier);
        if (supplierInDatabase.isPresent()) {
            supplier = supplierInDatabase.get();
        }

        Product product = generateTestProduct(supplier);
        Optional<Product> productInDatabase = createEntityInDatabase(product);
        if (productInDatabase.isPresent()) {
            product = productInDatabase.get();
        }

        orderProductTablePopulation(order, product);

        Customer customerUpdated = updateCustomerObject(customer);
        updateDatabase(customerUpdated);

        Order orderUpdated = updateOrderObject(order);
        updateDatabase(orderUpdated);

        Supplier supplierUpdated = updateSupplierObject(supplier);
        updateDatabase(supplierUpdated);

        Product productUpdated = updateProductObject(product);
        updateDatabase(productUpdated);

        Optional<Customer> optionalCustomer = findEntityInDataBase(Customer.class, 1);
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        }

        Optional<Order> optionalOrder = findEntityInDataBase(Order.class, 2);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        }

        Optional<Supplier> optionalSupplier = findEntityInDataBase(Supplier.class, 3);
        if (optionalSupplier.isPresent()) {
            supplier = optionalSupplier.get();
        }

        Optional<Product> optionalProduct = findEntityInDataBase(Product.class, 4);
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        }

        deleteEntityFromDataBase(Customer.class, 1);
        deleteEntityFromDataBase(Supplier.class, 1);
        deleteEntityFromDataBase(Customer.class, 2);
        deleteEntityFromDataBase(Supplier.class, 2);
    }

    private Customer generateTestCustomer() {
        long timeMillis = System.currentTimeMillis();
        String customerName = String.valueOf(timeMillis).substring(10, 13) + " Customer";
        String phone = String.valueOf(timeMillis).replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "+$1 ($2) $3-$4");
        return new Customer(customerName, phone);
    }

    private <T> Optional<T> createEntityInDatabase(T crudEntity) {
        boolean isSentToDatabase = createInDatabase(crudEntity);
        if (isSentToDatabase) {
            log.debug("Entity created in DataBase successfully: {}", crudEntity);
            return Optional.ofNullable(crudEntity);
        }

        log.debug("Entity not saved: {}", crudEntity);
        return Optional.empty();
    }

    private Order generateTestOrder(Customer customer) {
        String orderNumber = String.valueOf(System.currentTimeMillis()).substring(3, 13);
        BigDecimal totalAmount = BigDecimal.valueOf(System.currentTimeMillis() / 1000000000.0);
        int customerId = customer.getCustomerId();
        var orderDate = new Date();
        return new Order(orderNumber, customerId, orderDate, totalAmount);
    }

    private Supplier generateTestSupplier() {
        long timeMillis = System.currentTimeMillis();
        String companyName = String.valueOf(timeMillis).substring(10, 13) + " Company";
        String phone = String.valueOf(timeMillis).replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "+$1 ($2) $3-$4");
        return new Supplier(companyName, phone);
    }

    private Product generateTestProduct(Supplier supplier) {
        long timeMillis = System.currentTimeMillis();
        String productName = String.valueOf(timeMillis).substring(10, 13) + " Product";
        BigDecimal unitPrice = BigDecimal.valueOf(System.currentTimeMillis() / 100000000000.0);
        int supplierId = supplier.getSupplierId();
        return new Product(productName, supplierId, unitPrice, false);
    }

    private <T> boolean createInDatabase(T crudEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(crudEntity);
            transaction.commit();
            entityManager.close();
            log.debug("{} sent to DataBase successfully", crudEntity);
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
            entityManager.close();
        }
        log.debug("{} was not sent to database", crudEntity);
        return false;
    }

    private boolean orderProductTablePopulation(Order order, Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createNativeQuery("insert into liadov.orderproduct (orderid, productid) values (?,?)")
                    .setParameter(1, order.getOrderID())
                    .setParameter(2, product.getProductId())
                    .executeUpdate();
            transaction.commit();
            entityManager.close();
            log.debug("OrderProduct table populated");
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
            entityManager.close();
        }
        log.debug("OrderProduct was not populated");
        return false;
    }

    private Product updateProductObject(Product product) {
        log.trace("Product update initiated: {}", product);
        product.setUnitPrice(BigDecimal.valueOf(11.12));
        return product;
    }

    private Supplier updateSupplierObject(Supplier supplier) {
        log.trace("Supplier update initiated: {}", supplier);
        var stringBuilder = new StringBuilder(supplier.getCompanyName());
        stringBuilder.insert(0, "Updated ");
        supplier.setCompanyName(stringBuilder.toString());
        return supplier;
    }

    private Order updateOrderObject(Order order) {
        log.trace("Order update initiated: {}", order);
        order.setTotalAmount(BigDecimal.valueOf(21.234));
        return order;
    }

    private Customer updateCustomerObject(Customer customer) {
        log.trace("Customer update initiated: {}", customer);
        customer.setCustomerName(customer.getCustomerName() + " Updated");
        return customer;
    }

    private boolean updateDatabase(CrudEntity crudEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(crudEntity);
            transaction.commit();
            log.debug("object updated: {}", crudEntity);
            entityManager.close();
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("Error during DB transaction ", e);
            entityManager.close();
        }
        log.debug("object was not updated: {}", crudEntity);
        return false;
    }

    private <T> Optional<T> findEntityInDataBase(Class<T> entityClass, int primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            T crudEntity = entityManager.find(entityClass, primaryKey);
            entityManager.close();
            log.debug("Found customer = {}", crudEntity);
            return Optional.ofNullable(crudEntity);
        } catch (IllegalArgumentException e) {
            log.error("Error", e);
            entityManager.close();
        }
        log.debug("object not found");
        return Optional.empty();
    }

    private <T> boolean deleteEntityFromDataBase(Class<T> entityClass, int primaryKey) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        T entity = entityManager.find(entityClass, primaryKey);
        boolean dataBaseContainsEntity = entityManager.contains(entity);
        log.debug("object exist: {}", dataBaseContainsEntity);
        if (!dataBaseContainsEntity) {
            log.debug("object with primaryKey = [{}] does not exist", primaryKey);
            return false;
        }
        try {
            entityManager.remove(entity);
            transaction.commit();
            log.debug("object removed successfully");
            entityManager.close();
            return true;
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            log.error("DataBase transaction error", e);
            entityManager.close();
        }
        log.trace("object was not removed");
        return false;
    }
}
