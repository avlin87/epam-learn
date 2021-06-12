package com.epam.liadov.converter;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CustomerDtoToCustomerConverterTest - test for {@link CustomerDtoToCustomerConverter}
 *
 * @author Aleksandr Liadov
 */
class CustomerDtoToCustomerConverterTest {

    private CustomerDtoToCustomerConverter toCustomerConverter;

    @BeforeEach
    public void setup() {
        toCustomerConverter = new CustomerDtoToCustomerConverter();
    }

    @Test
    void convert() {
        var customerDto = new CustomerDto();
        customerDto.setCustomerId(1)
                .setCustomerName("CustomerName")
                .setPhone("1111-222");

        Customer customer = toCustomerConverter.convert(customerDto);

        assertEquals(customer.getCustomerId(), customerDto.getCustomerId());
        assertEquals(customer.getCustomerName(), customerDto.getCustomerName());
        assertEquals(customer.getPhone(), customerDto.getPhone());
    }
}