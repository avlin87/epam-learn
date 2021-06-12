package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * CustomerToCustomerDtoConverter - class converter from Customer to CustomerDto
 *
 * @author Aleksandr Liadov
 */
@Component
@Slf4j
public class CustomerToCustomerDtoConverter implements Converter<Customer, CustomerDto> {

    @Override
    public CustomerDto convert(@NotNull Customer customer) {
        var customerDto = new CustomerDto();

        int customerId = customer.getCustomerId();
        String customerName = customer.getCustomerName();
        String phone = customer.getPhone();
        customerDto.setCustomerId(customerId)
                .setCustomerName(customerName)
                .setPhone(phone);
        log.info("convert() - convert from '{}' to {}", customer, customerDto);
        return customerDto;
    }
}