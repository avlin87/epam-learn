package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CustomerToCustomerDtoConverterTest - test for {@link CustomerToCustomerDtoConverter}
 *
 * @author Aleksandr Liadov
 */
class CustomerToCustomerDtoConverterTest {

    private CustomerToCustomerDtoConverter toCustomerDtoConverter;

    @BeforeEach
    public void setup() {
        toCustomerDtoConverter = new CustomerToCustomerDtoConverter();
    }

    @Test
    void convert() {
        var customer = new Customer();
        customer.setCustomerId(1)
                .setCustomerName("CustomerName")
                .setPhone("1111-222");

        CustomerDto customerDto = toCustomerDtoConverter.convert(customer);

        assertEquals(customerDto.getCustomerId(), customer.getCustomerId());
        assertEquals(customerDto.getCustomerName(), customer.getCustomerName());
        assertEquals(customerDto.getPhone(), customer.getPhone());
    }
}