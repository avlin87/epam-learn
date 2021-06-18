package com.epam.liadov.resource.impl;

import com.epam.liadov.converter.ProductToProductDtoConverter;
import com.epam.liadov.domain.entity.Product;
import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.dto.ProductDto;
import com.epam.liadov.exception.NotFoundException;
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

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getProductResponse200() throws Exception {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        when(productService.find(1)).thenReturn(testProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("productId", is(testProduct.getProductId())).exists())
                .andExpect(jsonPath("productName", is(testProduct.getProductName())).exists())
                .andExpect(jsonPath("supplierId", is(testProduct.getSupplierId())).exists())
                .andExpect(jsonPath("unitPrice", is(testProduct.getUnitPrice())).exists())
                .andExpect(jsonPath("discontinued", is(testProduct.isDiscontinued())).exists());
    }

    @Test
    public void getProductResponse404() throws Exception {
        when(productService.find(anyInt())).thenThrow(new NotFoundException("Product does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.get("/product/999"))
                .andExpect(status().is(404));
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("productId", is(testProduct.getProductId())).exists())
                .andExpect(jsonPath("productName", is(testProduct.getProductName())).exists())
                .andExpect(jsonPath("supplierId", is(testProduct.getSupplierId())).exists())
                .andExpect(jsonPath("unitPrice", is(testProduct.getUnitPrice())).exists())
                .andExpect(jsonPath("discontinued", is(testProduct.isDiscontinued())).exists());
    }

    @Test
    void deleteProductResponse200() throws Exception {
        when(productService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProductResponse404() throws Exception {
        when(productService.delete(anyInt())).thenThrow(new NotFoundException("Product does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/1"))
                .andExpect(status().is(404));
    }

    @Test
    void updateProductResponse200() throws Exception {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("productId", is(testProduct.getProductId())).exists())
                .andExpect(jsonPath("productName", is(testProduct.getProductName())).exists())
                .andExpect(jsonPath("supplierId", is(testProduct.getSupplierId())).exists())
                .andExpect(jsonPath("unitPrice", is(testProduct.getUnitPrice())).exists())
                .andExpect(jsonPath("discontinued", is(testProduct.isDiscontinued())).exists());
    }

    @Test
    void updateProductResponse404() throws Exception {
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        when(productService.update(testProduct)).thenThrow(new NotFoundException("Product does not exist"));
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testProduct);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().is(404));
    }

    @Test
    void getAllProducts() throws Exception {
        var toProductDtoConverter = new ProductToProductDtoConverter();
        Supplier supplier = factory.generateTestSupplier();
        Product testProduct = factory.generateTestProduct(supplier);
        ProductDto testProductDto = toProductDtoConverter.convert(testProduct);
        List<Product> products = new ArrayList<>();
        List<ProductDto> productDtos = new ArrayList<>();
        products.add(testProduct);
        productDtos.add(testProductDto);
        when(productService.getAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/product"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productDtos)));
    }
}