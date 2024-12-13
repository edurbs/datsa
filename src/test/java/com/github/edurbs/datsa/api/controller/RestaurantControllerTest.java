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

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    private static final String RESTAURANT_URL = "/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantRegistryService restaurantRegistryService;

    @Test
    void whenGetInvalidUrl_thenStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/invalid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetValidRestaurantId_thenStatus200() throws Exception {
        Mockito.when(restaurantRegistryService.getById(1L)).thenReturn(Instancio.create(Restaurant.class));

        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenGetAll_andNoneRestaurant_thenStatus404() throws Exception {
        Mockito.when(restaurantRegistryService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void whenGetInvalidRestaurantId_thenStatus404() throws Exception {
        Mockito.when(restaurantRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(RESTAURANT_URL + "/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAddValidRestaurant_thenStatus201() throws Exception {
        var restaurantTest = Instancio.create(Restaurant.class);
        Mockito.when(restaurantRegistryService.save(Mockito.any(Restaurant.class))).thenReturn(restaurantTest);

        mockMvc.perform(MockMvcRequestBuilders.post(RESTAURANT_URL)
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(restaurantTest.getName()))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenDeleteValidRestaurant_thenStatus204() throws Exception {
        Mockito.doNothing().when(restaurantRegistryService).remove(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteInvalidRestaurant_thenStatus404() throws Exception {
        Mockito.doThrow(new ModelNotFoundException()).when(restaurantRegistryService).remove(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(RESTAURANT_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAlterValidrestaurant_thenStatus200() throws Exception {
        // given
        var restaurantOriginal = new Restaurant();
        restaurantOriginal.setId(1L);
        restaurantOriginal.setName("some name");

        var restaurantUpdated = new Restaurant();
        restaurantUpdated.setName("new name");

        Mockito.when(restaurantRegistryService.getById(restaurantOriginal.getId())).thenReturn(restaurantOriginal);
        Mockito.when(restaurantRegistryService.save(Mockito.any(Restaurant.class))).thenReturn(restaurantOriginal);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put(RESTAURANT_URL + "/" + restaurantOriginal.getId())
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(restaurantUpdated.getName())).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRegistryService).save(restaurantCaptor.capture());
        var alteredrestaurant = restaurantCaptor.getValue();
        assertEquals(restaurantUpdated.getName(), alteredrestaurant.getName());
        assertEquals(restaurantOriginal.getId(), alteredrestaurant.getId());
    }

    @Test
    void whenAlterInvalidrestaurant_thenStatus404() throws Exception {
        Mockito.when(restaurantRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.put(RESTAURANT_URL + "/999")
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted("new name")).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
