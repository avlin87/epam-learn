package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.entity.Order;
import com.epam.liadov.entity.Product;
import com.epam.liadov.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * CrudEntityFactoryTest test class for {@link EntityFactory}
 *
 * @author Aleksandr Liadov
 */
class EntityFactoryTest {

    private EntityFactory factory;

    @BeforeEach
    public void createCrudFactory() {
        factory = new EntityFactory();
    }

    @Test
    void generateTestCustomer() {
        Customer customer = factory.generateTestCustomer();

        assertNotNull(customer);
    }

    @Test
    void generateTestOrder() {
        Customer customer = factory.generateTestCustomer();

        Order order = factory.generateTestOrder(customer);

        assertNotNull(order);
    }

    @Test
    void generateTestSupplier() {
        Supplier supplier = factory.generateTestSupplier();

        assertNotNull(supplier);
    }

    @Test
    void generateTestProduct() {
        Supplier supplier = factory.generateTestSupplier();

        Product product = factory.generateTestProduct(supplier);

        assertNotNull(product);
    }

    @Test
    void updateProductObject() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("test Product");
        product.setDiscontinued(false);
        product.setSupplierId(1);
        product.setUnitPrice(BigDecimal.TEN);
        String productState = product.toString();

        Product productUpdated = factory.updateProductObject(product);
        String productUpdatedState = productUpdated.toString();

        assertFalse(productUpdatedState.equalsIgnoreCase(productState));
    }

    @Test
    void updateSupplierObject() {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1);
        supplier.setCompanyName("Tester");
        supplier.setPhone("1-111-111-1111");
        String supplierState = supplier.toString();

        Supplier supplierUpdated = factory.updateSupplierObject(supplier);
        String supplierUpdatedState = supplierUpdated.toString();

        assertFalse(supplierUpdatedState.equalsIgnoreCase(supplierState));
    }

    @Test
    void updateOrderObject() {
        Order order = new Order();
        order.setOrderID(70);
        order.setOrderDate(new Date());
        order.setTotalAmount(BigDecimal.ONE);
        order.setCustomerId(11);
        order.setOrderNumber("600");
        String orderState = order.toString();

        Order orderUpdated = factory.updateOrderObject(order);
        String orderUpdatedState = orderUpdated.toString();

        assertFalse(orderUpdatedState.equalsIgnoreCase(orderState));
    }

    @Test
    void updateCustomerObject() {
        Customer customer = factory.generateTestCustomer();
        customer.setCustomerId(41);
        customer.setCustomerName("Test Customer");
        customer.setPhone("55-11-1234");
        String customerState = customer.toString();

        Customer customerUpdated = factory.updateCustomerObject(customer);
        String customerUpdatedState = customerUpdated.toString();

        assertFalse(customerUpdatedState.equalsIgnoreCase(customerState));
    }
}