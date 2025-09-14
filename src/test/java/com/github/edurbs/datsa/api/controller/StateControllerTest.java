package com.github.edurbs.datsa.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.edurbs.datsa.api.dto.input.StateInput;
import com.github.edurbs.datsa.api.dto.output.StateOutput;
import com.github.edurbs.datsa.api.mapper.StateMapper;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.service.StateRegistryService;

@WebMvcTest(StateController.class)
class StateControllerTest {

    private static final String STATE_URL = "/states";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StateRegistryService stateRegistryService;

    @MockBean
    private StateMapper stateMapper;

    private State state;
    private StateOutput stateOutput;
    private StateInput stateInput;

    @BeforeEach
    void setup(){
        state = Instancio.create(State.class);

        stateOutput = new StateOutput();
        stateOutput.setId(state.getId());
        stateOutput.setName(state.getName());

        stateInput = new StateInput();
        stateInput.setName(state.getName());
    }

    @Test
    void newTest() throws Exception {

        when(stateRegistryService.getById(state.getId()))
            .thenReturn(state);
        when(stateMapper.toOutput(any(State.class)))
            .thenReturn(stateOutput);
        mockMvc.perform(get(STATE_URL+"/"+state.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(state.getId()))
            .andExpect(jsonPath("$.name").value(state.getName()));

    }

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

        mockMvc.perform(MockMvcRequestBuilders.get(STATE_URL + "/999"));
    }

    @Test
    void whenPostValidState_thenReturnsCreatedAndCorrectBody() throws Exception {
        String json = objectMapper.writeValueAsString(stateInput);

        when(stateMapper.toDomain(any(StateInput.class)))
            .thenReturn(state);
        when(stateRegistryService.save(any(State.class)))
            .thenReturn(state);
        when(stateMapper.toOutput(state))
            .thenReturn(stateOutput);
        MvcResult result = mockMvc.perform(post(STATE_URL)
                .content(json)
                .contentType(APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();
        verify(stateRegistryService, times(1)).save(any(State.class));
        String resultJson = result.getResponse().getContentAsString();
        StateOutput stateOutputResult = objectMapper.readValue(resultJson, StateOutput.class);
        assertThat(stateOutputResult)
            .usingRecursiveComparison()
            .isEqualTo(stateOutput);
    }

    @Test
    void whenAlterValidState_thenStatus200() throws Exception {
        var stateOriginal = new State();
        stateOriginal.setId(1L);
        stateOriginal.setName("some name");

        var stateUpdated = new State();
        stateUpdated.setName("new name");

        StateOutput stateOutput = new StateOutput();
        stateOutput.setId(stateUpdated.getId());
        stateOutput.setName(stateUpdated.getName());

        Mockito.when(stateRegistryService.getById(stateOriginal.getId())).thenReturn(stateOriginal);
        Mockito.when(stateRegistryService.save(Mockito.any(State.class))).thenReturn(stateUpdated);

        mockMvc.perform(MockMvcRequestBuilders.put(STATE_URL + "/" + stateOriginal.getId())
                .content("""
                        {
                            "name":"%s"
                        }
                        """.formatted(stateUpdated.getName())).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

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


