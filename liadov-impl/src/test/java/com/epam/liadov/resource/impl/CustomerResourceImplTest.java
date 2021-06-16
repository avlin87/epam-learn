package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.factory.EntityFactory;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockBean
    private CustomerService customerService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getCustomer() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        when(customerService.find(1)).thenReturn(testCustomer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/1"))
                .andExpect(status().isOk());
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
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomer() throws Exception {
        when(customerService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
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
                .andExpect(status().isOk());
    }

    @Test
    void getAllCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer"))
                .andExpect(status().isOk());
    }
}