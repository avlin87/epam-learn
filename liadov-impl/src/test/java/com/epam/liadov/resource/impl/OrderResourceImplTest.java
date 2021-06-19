package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.OrderToOrderDtoConverter;
import com.epam.liadov.domain.entity.Customer;
import com.epam.liadov.domain.entity.Order;
import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.dto.OrderDto;
import com.epam.liadov.exception.NotFoundException;
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
import java.util.stream.Collectors;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getOrderResponse200() throws Exception {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(orderService.find(1)).thenReturn(testOrder);
        List<Integer> testOrderProductId = testOrder.getProductId()
                .stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderID", is(testOrder.getOrderID())).exists())
                .andExpect(jsonPath("orderDate", is(testOrder.getOrderDate())).exists())
                .andExpect(jsonPath("customerId", is(testOrder.getCustomerId())).exists())
                .andExpect(jsonPath("orderNumber", is(testOrder.getOrderNumber())).exists())
                .andExpect(jsonPath("totalAmount", is(testOrder.getTotalAmount())).exists())
                .andExpect(jsonPath("productId", is(testOrderProductId)).exists());
    }

    @Test
    public void getOrderResponse404() throws Exception {
        when(orderService.find(anyInt())).thenThrow(new NotFoundException("Order does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.get("/order/999"))
                .andExpect(status().is(404));
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
        List<Integer> testOrderProductId = testOrder.getProductId()
                .stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderID", is(testOrder.getOrderID())).exists())
                .andExpect(jsonPath("orderDate", is(testOrder.getOrderDate())).exists())
                .andExpect(jsonPath("customerId", is(testOrder.getCustomerId())).exists())
                .andExpect(jsonPath("orderNumber", is(testOrder.getOrderNumber())).exists())
                .andExpect(jsonPath("totalAmount", is(testOrder.getTotalAmount())).exists())
                .andExpect(jsonPath("productId", is(testOrderProductId)).exists());
    }

    @Test
    void deleteOrderResponse200() throws Exception {
        when(orderService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrderResponse404() throws Exception {
        when(orderService.delete(anyInt())).thenThrow(new NotFoundException("Order does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1"))
                .andExpect(status().is(404));
    }

    @Test
    void updateOrderResponse200() throws Exception {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(orderService.update(testOrder)).thenReturn(testOrder);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testOrder);
        List<Integer> testOrderProductId = testOrder.getProductId()
                .stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        mockMvc.perform(
                MockMvcRequestBuilders.put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("orderID", is(testOrder.getOrderID())).exists())
                .andExpect(jsonPath("orderDate", is(testOrder.getOrderDate())).exists())
                .andExpect(jsonPath("customerId", is(testOrder.getCustomerId())).exists())
                .andExpect(jsonPath("orderNumber", is(testOrder.getOrderNumber())).exists())
                .andExpect(jsonPath("totalAmount", is(testOrder.getTotalAmount())).exists())
                .andExpect(jsonPath("productId", is(testOrderProductId)).exists());
    }

    @Test
    void updateOrderResponse404() throws Exception {
        Customer customer = factory.generateTestCustomer();
        Order testOrder = factory.generateTestOrder(customer);
        when(orderService.delete(anyInt())).thenThrow(new NotFoundException("Order does not exist"));
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testOrder);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().is(404));
    }

    @Test
    void findByCustomerId() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        var toOrderDtoConverter = new OrderToOrderDtoConverter();
        Order testOrder = factory.generateTestOrder(testCustomer);
        OrderDto testOrderDto = toOrderDtoConverter.convert(testOrder);
        List<Order> orders = new ArrayList<>();
        orders.add(testOrder);
        List<OrderDto> orderDtos = new ArrayList<>();
        orderDtos.add(testOrderDto);
        when(orderService.findByCustomerId(1)).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderDtos)));
    }

    @Test
    void getAllOrders() throws Exception {
        Customer testCustomer = factory.generateTestCustomer();
        var toOrderDtoConverter = new OrderToOrderDtoConverter();
        Order testOrder = factory.generateTestOrder(testCustomer);
        OrderDto testOrderDto = toOrderDtoConverter.convert(testOrder);
        List<Order> orders = new ArrayList<>();
        orders.add(testOrder);
        List<OrderDto> orderDtos = new ArrayList<>();
        orderDtos.add(testOrderDto);
        when(orderService.getAll()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(orderDtos)));
    }
}