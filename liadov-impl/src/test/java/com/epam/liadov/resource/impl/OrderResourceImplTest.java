package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.resource.OrderResource;
import com.epam.liadov.service.OrderService;
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
 * OrderResourceImplTest - test for {@link OrderResourceImpl}
 *
 * @author Aleksandr Liadov
 */
@WebMvcTest(OrderResource.class)
@RunWith(SpringRunner.class)
class OrderResourceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getOrder() throws Exception {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(orderService.find(1)).thenReturn(testOrder);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addOrder() throws Exception {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(orderService.save(testOrder)).thenReturn(testOrder);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testOrder);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder() throws Exception {
        when(orderService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateOrder() throws Exception {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(orderService.update(testOrder)).thenReturn(testOrder);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testOrder);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk());
    }

    @Test
    void getAllOrders() throws Exception {
        List<Order> orders = new ArrayList<>();
        when(orderService.getAll()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andExpect(status().isOk());
    }
}