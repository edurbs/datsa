package com.github.edurbs.datsa.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    private static final String END_POINT_PATH = "/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantRegistryService restaurantRegistryService;

    @Test
    void whenGetInvalidRestaurant_thenReturnStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/invalid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetValidRestaurantId_thenStatus200() throws Exception {
        Mockito.when(restaurantRegistryService.getById(1L)).thenReturn(Instancio.create(Restaurant.class));

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void whenGetAll_andNoneRestaurant_thenStatus404() throws Exception {
        Mockito.when(restaurantRegistryService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void whenGetInvalidRestaurantId_thenStatus404() throws Exception {
        Mockito.when(restaurantRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(END_POINT_PATH + "/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAddValidRestaurant_thenStatus201() throws Exception {
        var restaurantTest = Instancio.create(Restaurant.class);
        Mockito.when(restaurantRegistryService.save(Mockito.any(Restaurant.class))).thenReturn(restaurantTest);

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                .content("""
                        {
                            "name": "%s",
                            "shippingFee": 1,
                            "kitchen": {
                                "id": 1
                            }
                        }
                        """.formatted(restaurantTest.getName()))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    void whenAddRestaurantWithNullKitchen_thenStatus400() throws Exception {
        var restaurant = Instancio.create(Restaurant.class);
        //restaurant.setKitchen(null);

        String json = objectMapper.writeValueAsString(restaurant);

        Mockito.when(restaurantRegistryService.save(restaurant))
                .thenThrow(new ModelValidationException());
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                .content(json)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(restaurantRegistryService, times(1)).save(restaurant);
    }
    @Test
    void whenAddRestaurantWithKitchenWithoutId_thenStatus404() throws Exception {
        var restaurantTest = new Restaurant();
        restaurantTest.setId(1L);
        restaurantTest.setName("some name");
        var kitchenTest = new Kitchen();
        kitchenTest.setId(1L);
        restaurantTest.setKitchen(kitchenTest);

        var restaurantTest2 = new Restaurant();
        restaurantTest2.setId(1L);
        restaurantTest2.setName("another name");
        var kitchenTest2 = new Kitchen();
        kitchenTest2.setId(1L);
        restaurantTest2.setKitchen(kitchenTest2);

        String json = new ObjectMapper().writeValueAsString(restaurantTest);

        Mockito.when(restaurantRegistryService.save(restaurantTest2))
                .thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                .content(json)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void whenDeleteValidRestaurant_thenStatus204() throws Exception {
        Mockito.doNothing().when(restaurantRegistryService).remove(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteInvalidRestaurant_thenStatus404() throws Exception {
        Mockito.doThrow(new ModelNotFoundException()).when(restaurantRegistryService).remove(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAlterValidRestaurant_thenStatus200() throws Exception {
        // given
        var restaurantOriginal = new Restaurant();
        restaurantOriginal.setId(1L);
        restaurantOriginal.setName("some name");

        var restaurantUpdated = new Restaurant();
        restaurantUpdated.setName("new name");

        Mockito.when(restaurantRegistryService.getById(restaurantOriginal.getId())).thenReturn(restaurantOriginal);
        Mockito.when(restaurantRegistryService.save(Mockito.any(Restaurant.class))).thenReturn(restaurantOriginal);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH + "/" + restaurantOriginal.getId())
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(restaurantUpdated.getName())).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // then
        ArgumentCaptor<Restaurant> restaurantCaptor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRegistryService).save(restaurantCaptor.capture());
        var alteredRestaurant = restaurantCaptor.getValue();
        assertEquals(restaurantUpdated.getName(), alteredRestaurant.getName());
        assertEquals(restaurantOriginal.getId(), alteredRestaurant.getId());
    }

    @Test
    void whenAlterNotFoundRestaurant_thenStatus404() throws Exception {
        Mockito.when(restaurantRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH + "/999")
                .content("""
                        {
                            "name":"new name"
                        }
                        """)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
