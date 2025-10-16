package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.mapper.CityMapper;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.service.CityRegistryService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(CityController.class)
class CityControllerTest {

    private static final String CITY_URL = "/cities";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CityRegistryService cityRegistryService;

    @MockitoBean
    private CityMapper cityMapper;

    @Test
    void whenGetInvalidUrl_thenStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/invalid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(CITY_URL))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetValidCityId_thenStatus200() throws Exception {
        Mockito.when(cityRegistryService.getById(1L)).thenReturn(Instancio.create(City.class));

        mockMvc.perform(MockMvcRequestBuilders.get(CITY_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetInvalidCityId_thenStatus404() throws Exception {
        Mockito.when(cityRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(CITY_URL + "/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAddValidCity_thenStatus201() throws Exception {
        var cityTest = Instancio.create(City.class);
        Mockito.when(cityRegistryService.save(Mockito.any(City.class))).thenReturn(cityTest);

        mockMvc.perform(MockMvcRequestBuilders.post(CITY_URL)
                .content("""
                        {
                            "name":"%s",
                            "state": {
                                "id": 1
                            }
                        }
                        """.formatted(cityTest.getName()))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenAlterValidCity_thenStatus200() throws Exception {
        var cityOriginal = new City();
        cityOriginal.setId(1L);
        cityOriginal.setName("some name");

        var cityUpdated = new City();
        cityUpdated.setName("new name");

        Mockito.when(cityRegistryService.getById(cityOriginal.getId())).thenReturn(cityOriginal);
        Mockito.when(cityRegistryService.save(Mockito.any(City.class))).thenReturn(cityOriginal);

        mockMvc.perform(MockMvcRequestBuilders.put(CITY_URL + "/" + cityOriginal.getId())
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(cityUpdated.getName())).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<City> cityCaptor = ArgumentCaptor.forClass(City.class);
        Mockito.verify(cityRegistryService).save(cityCaptor.capture());
        var alteredCity = cityCaptor.getValue();
        assertEquals(cityUpdated.getName(), alteredCity.getName());
        assertEquals(cityOriginal.getId(), alteredCity.getId());
    }

    @Test
    void whenDeleteValidCity_thenStatus204() throws Exception {
        Mockito.doNothing().when(cityRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(CITY_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteInvalidCity_thenStatus404() throws Exception {
        Mockito.doThrow(new ModelNotFoundException()).when(cityRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(CITY_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenDeleteModelInUse_thenStatus409() throws Exception {
        Mockito.doThrow(new ModelInUseException()).when(cityRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(CITY_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void whenDeleteCityWithInvalidState_thenStatus400() throws Exception {
        Mockito.doThrow(new ModelValidationException()).when(cityRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(CITY_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

