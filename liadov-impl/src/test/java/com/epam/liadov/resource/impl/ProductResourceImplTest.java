package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.resource.ProductResource;
import com.epam.liadov.service.ProductService;
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
 * ProductResourceImplTest - test for {@link ProductResourceImpl}
 *
 * @author Aleksandr Liadov
 */
@WebMvcTest(ProductResource.class)
@RunWith(SpringRunner.class)
class ProductResourceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getProduct() throws Exception {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        when(productService.find(1)).thenReturn(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(status().isOk());
    }

    @Test
    void addProduct() throws Exception {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        when(productService.save(testProduct)).thenReturn(testProduct);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        when(productService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct() throws Exception {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        when(productService.update(testProduct)).thenReturn(testProduct);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk());
    }

    @Test
    void getAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        when(productService.getAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
                .andExpect(status().isOk());
    }
}