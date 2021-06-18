package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.exception.NotFoundException;
import com.epam.liadov.resource.CustomerResource;
import com.epam.liadov.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CustomerResourceImplTest - test for {@link CustomerResourceImpl}
 *
 * @author Aleksandr Liadov
 */
@WebMvcTest(CustomerResource.class)
@RunWith(SpringRunner.class)
class CustomerResourceImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getCustomerResponse200() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.find(1)).thenReturn(testCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("customerId", is(testCustomer.getCustomerId())).exists())
                .andExpect(jsonPath("customerName", is(testCustomer.getCustomerName())).exists())
                .andExpect(jsonPath("phone", is(testCustomer.getPhone())).exists());
    }

    @Test
    public void getCustomerResponse404() throws Exception {
        when(customerService.find(anyInt())).thenThrow(new NotFoundException("Customer does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/999"))
                .andExpect(status().is(404));
    }

    @Test
    void addCustomer() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.save(testCustomer)).thenReturn(testCustomer);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testCustomer);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("customerId", is(testCustomer.getCustomerId())).exists())
                .andExpect(jsonPath("customerName", is(testCustomer.getCustomerName())).exists())
                .andExpect(jsonPath("phone", is(testCustomer.getPhone())).exists());
    }

    @Test
    void deleteCustomerResponse200() throws Exception {
        when(customerService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerResponse404() throws Exception {
        when(customerService.delete(anyInt())).thenThrow(new NotFoundException("Customer does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/1"))
                .andExpect(status().is(404));
    }

    @Test
    void updateCustomerResponse200() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.update(testCustomer)).thenReturn(testCustomer);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testCustomer);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("customerId", is(testCustomer.getCustomerId())).exists())
                .andExpect(jsonPath("customerName", is(testCustomer.getCustomerName())).exists())
                .andExpect(jsonPath("phone", is(testCustomer.getPhone())).exists());
    }

    @Test
    void updateCustomerResponse404() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.update(testCustomer)).thenThrow(new NotFoundException("Customer does not exist"));
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testCustomer);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().is(404));
    }

    @Test
    void getAllCustomers() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        List<Customer> customers = new ArrayList<>();
        customers.add(testCustomer);
        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(customers)));
    }
}