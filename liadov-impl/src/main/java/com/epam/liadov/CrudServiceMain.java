package com.epam.liadov;

import com.epam.liadov.entity.Customer;
import com.epam.liadov.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * CrudServiceMain - class initialization and demonstration of CRUD operations
 *
 * @author Aleksandr Liadov
 */
@Slf4j
public class CrudServiceMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.liadov");
        Customer customer = context.getBean(Customer.class);
        customer.setPhone("111-1112");
        customer.setCustomerName("Polll");
        System.out.println(customer);
        CustomerRepository customerRepository = context.getBean(CustomerRepository.class);
        boolean saveResult = customerRepository.save(customer);
        List<Customer> getAllResult = customerRepository.getAll();
        System.out.println(saveResult);
    }

}