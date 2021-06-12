package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * CustomerDtoToCustomerConverter - class converter from CustomerDto to Customer
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class CustomerDtoToCustomerConverter implements Converter<CustomerDto, Customer> {

    @Override
    public Customer convert(@NotNull CustomerDto customerDto) {
        var customer = new Customer();

        int customerId = customerDto.getCustomerId();
        String customerName = customerDto.getCustomerName();
        String phone = customerDto.getPhone();
        customer.setCustomerId(customerId)
                .setCustomerName(customerName)
                .setPhone(phone);
        log.info("convert() - convert from '{}' to {}", customerDto, customer);
        return customer;
    }
}