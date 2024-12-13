package com.github.edurbs.datsa.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.hamcrest.Matchers;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.service.KitchenRegistryService;

@WebMvcTest(KitchenController.class)
class KitchenControllerTest {

    private static final String KITCHEN_URL = "/kitchens";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KitchenRegistryService kitchenRegistryService;

    @Test
    void whenGetInvalidUrl_thenStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/invalid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(KITCHEN_URL))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void whenGetAll_andNoneKitchen_thenStatus404() throws Exception {
        Mockito.when(kitchenRegistryService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(KITCHEN_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void whenGetValidKitchenId_thenStatus200() throws Exception {
        Mockito.when(kitchenRegistryService.getById(1L)).thenReturn(Instancio.create(Kitchen.class));

        mockMvc.perform(MockMvcRequestBuilders.get(KITCHEN_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetInvalidKitchenId_thenStatus404() throws Exception {
        Mockito.when(kitchenRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(KITCHEN_URL + "/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAddValidKitchen_thenStatus201() throws Exception {
        var kitchenTest = Instancio.create(Kitchen.class);
        Mockito.when(kitchenRegistryService.save(Mockito.any(Kitchen.class))).thenReturn(kitchenTest);

        mockMvc.perform(MockMvcRequestBuilders.post(KITCHEN_URL)
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(kitchenTest.getName()))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenAlterValidKitchen_thenStatus200() throws Exception {
        // given
        var kitchenOriginal = new Kitchen();
        kitchenOriginal.setId(1L);
        kitchenOriginal.setName("some name");

        var kitchenUpdated = new Kitchen();
        kitchenUpdated.setName("new name");

        Mockito.when(kitchenRegistryService.getById(kitchenOriginal.getId())).thenReturn(kitchenOriginal);
        Mockito.when(kitchenRegistryService.save(Mockito.any(Kitchen.class))).thenReturn(kitchenOriginal);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put(KITCHEN_URL + "/" + kitchenOriginal.getId())
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(kitchenUpdated.getName())).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        ArgumentCaptor<Kitchen> kitchenCaptor = ArgumentCaptor.forClass(Kitchen.class);
        Mockito.verify(kitchenRegistryService).save(kitchenCaptor.capture());
        var alteredKitchen = kitchenCaptor.getValue();
        assertEquals(kitchenUpdated.getName(), alteredKitchen.getName());
        assertEquals(kitchenOriginal.getId(), alteredKitchen.getId());
    }

    @Test
    void whenAlterInvalidKitchen_thenStatus404() throws Exception {
        Mockito.when(kitchenRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.put(KITCHEN_URL + "/999")
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted("new name")).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenDeleteValidKitchen_thenStatus200() throws Exception {
        Mockito.doNothing().when(kitchenRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(KITCHEN_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        testRemoveArgument();
    }

    @Test
    void whenDeleteInvalidKitchen_thenStatus404() throws Exception {
        Mockito.doThrow(new ModelNotFoundException()).when(kitchenRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(KITCHEN_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        testRemoveArgument();
    }

    @Test
    void whenDeleteModelInUse_thenStatus409() throws Exception {
        Mockito.doThrow(new ModelInUseException()).when(kitchenRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(KITCHEN_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
        testRemoveArgument();
    }

    private void testRemoveArgument() {
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(kitchenRegistryService).remove(idCaptor.capture());
        assertEquals(1L, idCaptor.getValue());
    }

}
