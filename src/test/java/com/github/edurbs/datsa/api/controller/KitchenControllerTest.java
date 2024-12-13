package com.github.edurbs.datsa.api.controller;

import java.util.Collections;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;

@WebMvcTest(KitchenController.class)
class KitchenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KitchenRegistryService kitchenRegistryService;

    @Test
    void whenGetInvalidUrl_thenStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/kitchen"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/kitchens"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void whenGetValidKitchenId_thenStatus200() throws Exception {
        Mockito.when(kitchenRegistryService.getById(1L)).thenReturn(Instancio.create(Kitchen.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/kitchens/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetInvalidKitchenId_thenStatus404() throws Exception {
        Mockito.when(kitchenRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/kitchens/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
