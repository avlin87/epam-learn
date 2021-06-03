package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        Customer customer = context.getBean(Customer.class);
        customer.setPhone("111-1112");
        customer.setCustomerName("Poll");
        log.debug("customer created as Bean: {}", customer);
        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
        boolean saveResult = customerRepository.save(customer);
        log.debug("customer saved: {}", saveResult);
        List<Customer> getAllResult = customerRepository.getAll();
    }

}