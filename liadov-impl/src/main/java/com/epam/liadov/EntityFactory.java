package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * EntityFactory - class to generate/update test POJO objects
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Component
public class EntityFactory {
    /**
     * Method generate new Customer based on System.currentTimeMillis()
     *
     * @return Customer
     */
    public Customer generateTestCustomer() {
        long timeMillis = System.currentTimeMillis();
        String customerName = String.valueOf(timeMillis).substring(10, 13) + " Customer";
        String phone = String.valueOf(timeMillis).replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "+$1 ($2) $3-$4");
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setPhone(phone);
        return customer;
    }

    /**
     * Method generate new Order based on System.currentTimeMillis()
     *
     * @param customer related Customer object
     * @return Order
     */
    public Order generateTestOrder(@NonNull Customer customer) {
        String orderNumber = String.valueOf(System.currentTimeMillis()).substring(3, 13);
        BigDecimal totalAmount = BigDecimal.valueOf(System.currentTimeMillis() / 1000000000.0);
        int customerId = customer.getCustomerId();
        var orderDate = new Date();
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setCustomerId(customerId);
        order.setOrderDate(orderDate);
        order.setTotalAmount(totalAmount);
        return order;
    }

    /**
     * Method generate new Supplier object based on System.currentTimeMillis()
     *
     * @return Supplier
     */
    public Supplier generateTestSupplier() {
        long timeMillis = System.currentTimeMillis();
        String companyName = String.valueOf(timeMillis).substring(10, 13) + " Company";
        String phone = String.valueOf(timeMillis).replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d+)", "+$1 ($2) $3-$4");
        Supplier supplier = new Supplier();
        supplier.setCompanyName(companyName);
        supplier.setPhone(phone);
        return supplier;
    }

    /**
     * Method generate new Product object based on System.currentTimeMillis()
     *
     * @param supplier related Supplier object
     * @return Product
     */
    public Product generateTestProduct(@NonNull Supplier supplier) {
        long timeMillis = System.currentTimeMillis();
        String productName = String.valueOf(timeMillis).substring(10, 13) + " Product";
        BigDecimal unitPrice = BigDecimal.valueOf(System.currentTimeMillis() / 100000000000.0);
        int supplierId = supplier.getSupplierId();
        Product product = new Product();
        product.setProductName(productName);
        product.setSupplierId(supplierId);
        product.setUnitPrice(unitPrice);
        product.setDiscontinued(false);
        return product;
    }

    /**
     * Method update unitPrice filed of Product class object
     *
     * @param product target object
     * @return updated product object
     */
    public Product updateProductObject(@NonNull Product product) {
        log.trace("Product update initiated: {}", product);
        product.setUnitPrice(BigDecimal.valueOf(11.12));
        return product;
    }

    /**
     * Method update companyName filed of Supplier class object
     *
     * @param supplier target object
     * @return updated supplier object
     */
    public Supplier updateSupplierObject(@NonNull Supplier supplier) {
        log.trace("Supplier update initiated: {}", supplier);
        var stringBuilder = new StringBuilder(supplier.getCompanyName());
        stringBuilder.insert(0, "Updated ");
        supplier.setCompanyName(stringBuilder.toString());
        return supplier;
    }

    /**
     * Method update totalAmount filed of Order class object
     *
     * @param order target object
     * @return updated object
     */
    public Order updateOrderObject(@NonNull Order order) {
        log.trace("Order update initiated: {}", order);
        order.setTotalAmount(BigDecimal.valueOf(21.234));
        return order;
    }

    /**
     * Method update customerName filed of Customer object
     *
     * @param customer target object
     * @return updated object
     */
    public Customer updateCustomerObject(@NonNull Customer customer) {
        log.trace("Customer update initiated: {}", customer);
        customer.setCustomerName(customer.getCustomerName() + " Updated");
        return customer;
    }
}
