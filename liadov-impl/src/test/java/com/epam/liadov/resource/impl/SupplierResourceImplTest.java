package com.epam.liadov.resource.impl;

import com.epam.liadov.domain.entity.Supplier;
import com.epam.liadov.domain.entity.factory.EntityFactory;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockBean
    private SupplierService supplierService;
    private final EntityFactory factory = new EntityFactory();

    @Test
    public void getSupplier() throws Exception {
        Supplier testSupplier = factory.generateTestSupplier();
        when(supplierService.find(1)).thenReturn(testSupplier);

        mockMvc.perform(MockMvcRequestBuilders.get("/supplier/1"))
                .andExpect(status().isOk());
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
                .andExpect(status().isOk());
    }

    @Test
    void deleteSupplier() throws Exception {
        when(supplierService.delete(anyInt())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/supplier/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateSupplier() throws Exception {
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
                .andExpect(status().isOk());
    }

    @Test
    void getAllSuppliers() throws Exception {
        List<Supplier> suppliers = new ArrayList<>();
        when(supplierService.getAll()).thenReturn(suppliers);

        mockMvc.perform(MockMvcRequestBuilders.get("/supplier"))
                .andExpect(status().isOk());
    }
}