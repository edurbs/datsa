package com.github.edurbs.datsa.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.service.StateRegistryService;

@WebMvcTest(StateController.class)
public class StateControllerTest {

    private static final String STATE_URL = "/states";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StateRegistryService stateRegistryService;

    @Test
    void whenGetInvalidUrl_thenStatus404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/invalid"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(STATE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetValidStateId_thenStatus200() throws Exception {
        Mockito.when(stateRegistryService.getById(1L)).thenReturn(Instancio.create(State.class));

        mockMvc.perform(MockMvcRequestBuilders.get(STATE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void whenGetInvalidStateId_thenStatus404() throws Exception {
        Mockito.when(stateRegistryService.getById(999L)).thenThrow(new ModelNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get(STATE_URL + "/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenAddValidState_thenStatus201() throws Exception {
        var stateTest = Instancio.create(State.class);
        Mockito.when(stateRegistryService.save(Mockito.any(State.class))).thenReturn(stateTest);

        mockMvc.perform(MockMvcRequestBuilders.post(STATE_URL)
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(stateTest.getName()))
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void whenAlterValidState_thenStatus200() throws Exception {
        var stateOriginal = new State();
        stateOriginal.setId(1L);
        stateOriginal.setName("some name");

        var stateUpdated = new State();
        stateUpdated.setName("new name");

        Mockito.when(stateRegistryService.getById(stateOriginal.getId())).thenReturn(stateOriginal);
        Mockito.when(stateRegistryService.save(Mockito.any(State.class))).thenReturn(stateOriginal);

        mockMvc.perform(MockMvcRequestBuilders.put(STATE_URL + "/" + stateOriginal.getId())
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(stateUpdated.getName())).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<State> stateCaptor = ArgumentCaptor.forClass(State.class);
        Mockito.verify(stateRegistryService).save(stateCaptor.capture());
        var alteredState = stateCaptor.getValue();
        assertEquals(stateUpdated.getName(), alteredState.getName());
        assertEquals(stateOriginal.getId(), alteredState.getId());
    }

    @Test
    void whenDeleteValidState_thenStatus204() throws Exception {
        Mockito.doNothing().when(stateRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(STATE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void whenDeleteInvalidState_thenStatus404() throws Exception {
        Mockito.doThrow(new ModelNotFoundException()).when(stateRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(STATE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenDeleteModelInUse_thenStatus409() throws Exception {
        Mockito.doThrow(new ModelInUseException()).when(stateRegistryService).remove(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(STATE_URL + "/1"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}


