package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.dto.input.StateInput;
import com.github.edurbs.datsa.api.v1.dto.output.StateOutput;
import com.github.edurbs.datsa.api.v1.mapper.StateMapper;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.service.StateRegistryService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StateController.class)
class StateControllerTest {

    private static final String STATE_URL = "/states";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StateRegistryService stateRegistryService;

    @MockitoBean
    private StateMapper stateMapper;

    private ControllerTestUtils<StateOutput> myUtils = new ControllerTestUtils<>(StateOutput.class);

    private State state;
    private StateOutput stateOutput;
    private StateInput stateInput;

    @BeforeEach
    void setup() {
        stateInput = Instancio.create(StateInput.class);
        state = new State();
        state.setId(Instancio.create(Long.class));
        state.setName(stateInput.getName());
        stateOutput = new StateOutput();
        stateOutput.setId(state.getId());
        stateOutput.setName(state.getName());
    }

    @Test
    void whenGetValid_thenStatusOkAndEqualsObject() throws Exception {
        when(stateMapper.toModel(any()))
                .thenReturn(stateOutput);
        StateOutput result = myUtils.fromResult(mockMvc.perform(get(getUrlId()))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(stateOutput);
    }

    @Test
    void whenGetInvalidUrl_thenStatus404() throws Exception {
        mockMvc.perform(get("/invalid"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetAll_thenStatus200() throws Exception {
        mockMvc.perform(get(STATE_URL))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetInvalidStateId_thenStatus404() throws Exception {
        when(stateRegistryService.getById(state.getId()))
                .thenThrow(new ModelNotFoundException());
        mockMvc.perform(get(getUrlId()))
                .andExpect(status().isNotFound());

    }

    @Test
    void whenPostValidState_thenReturnsCreatedAndCorrectBody() throws Exception {
        String json = myUtils.toJson(stateInput);
        when(stateMapper.toModel(any()))
                .thenReturn(stateOutput);
        StateOutput stateOutputResult = myUtils.fromResult(
                mockMvc.perform(post(STATE_URL)
                        .content(json)
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andReturn());
        verify(stateRegistryService, times(1)).save(any());
        assertThat(stateOutputResult)
                .usingRecursiveComparison()
                .isEqualTo(stateOutput);
    }

    @Test
    void whenAlterValidState_thenStatus200() throws Exception {
        stateOutput.setName("new name");
        when(stateMapper.toModel(any()))
                .thenReturn(stateOutput);
        StateOutput stateOutputResult = myUtils.fromResult(mockMvc.perform(MockMvcRequestBuilders.put(getUrlId())
                .content(myUtils.toJson(stateInput))
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn());
        assertThat(stateOutputResult)
                .usingRecursiveComparison()
                .isEqualTo(stateOutput);

    }

    @Test
    void whenDeleteValidState_thenStatus204() throws Exception {
        mockMvc.perform(delete(getUrlId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteInvalidState_thenStatus404() throws Exception {
        doThrow(new ModelNotFoundException())
                .when(stateRegistryService)
                .remove(state.getId());
        mockMvc.perform(delete(getUrlId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteModelInUse_thenStatus409() throws Exception {
        doThrow(new ModelInUseException())
                .when(stateRegistryService)
                .remove(state.getId());
        mockMvc.perform(delete(getUrlId()))
                .andExpect(status().isConflict());
    }

    private String getUrlId() {
        return STATE_URL + "/" + state.getId();
    }

}
