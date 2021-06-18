package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
import com.epam.liadov.exception.NotFoundException;
import com.epam.liadov.resource.SupplierResource;
import com.epam.liadov.service.SupplierService;
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
 * SupplierResourceImplTest - test for {@link SupplierResourceImpl}
 *
 * @author Aleksandr Liadov
 */
@WebMvcTest(SupplierResource.class)
@RunWith(SpringRunner.class)
class SupplierResourceImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SupplierService supplierService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getSupplierResponse200() throws Exception {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.find(1)).thenReturn(testSupplier);

        mockMvc.perform(MockMvcRequestBuilders.get("/supplier/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("supplierId", is(testSupplier.getSupplierId())).exists())
                .andExpect(jsonPath("companyName", is(testSupplier.getCompanyName())).exists())
                .andExpect(jsonPath("phone", is(testSupplier.getPhone())).exists());
    }

    @Test
    public void getSupplierResponse404() throws Exception {
        when(supplierService.find(anyInt())).thenThrow(new NotFoundException("Supplier does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.get("/supplier/999"))
                .andExpect(status().is(404));
    }

    @Test
    void addSupplier() throws Exception {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.save(testSupplier)).thenReturn(testSupplier);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testSupplier);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("supplierId", is(testSupplier.getSupplierId())).exists())
                .andExpect(jsonPath("companyName", is(testSupplier.getCompanyName())).exists())
                .andExpect(jsonPath("phone", is(testSupplier.getPhone())).exists());
    }

    @Test
    void deleteSupplierResponse200() throws Exception {
        when(supplierService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/supplier/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteSupplierResponse404() throws Exception {
        when(supplierService.delete(anyInt())).thenThrow(new NotFoundException("Supplier does not exist"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/supplier/1"))
                .andExpect(status().is(404));
    }

    @Test
    void updateSupplierResponse200() throws Exception {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.update(testSupplier)).thenReturn(testSupplier);
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testSupplier);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("supplierId", is(testSupplier.getSupplierId())).exists())
                .andExpect(jsonPath("companyName", is(testSupplier.getCompanyName())).exists())
                .andExpect(jsonPath("phone", is(testSupplier.getPhone())).exists());
    }

    @Test
    void updateSupplierResponse404() throws Exception {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.update(testSupplier)).thenThrow(new NotFoundException("Supplier does not exist"));
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(testSupplier);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/supplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().is(404));
    }

    @Test
    void getAllSuppliers() throws Exception {
        Supplier testSupplier = factory.generateTestSupplier();
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(testSupplier);
        when(supplierService.getAll()).thenReturn(suppliers);

        mockMvc.perform(MockMvcRequestBuilders.get("/supplier"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(suppliers)));
    }
}