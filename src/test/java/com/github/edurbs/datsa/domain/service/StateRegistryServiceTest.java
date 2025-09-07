package com.github.edurbs.datsa.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.Optional;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.infra.repository.StateRepository;

@SpringBootTest
class StateRegistryServiceTest {

    @Autowired
    private StateRegistryService sut;

    @MockBean
    private StateRepository stateRepository;

    @Test
    void whenAddValidState_thenReturnState() {
        var state = Instancio.create(State.class);
        Mockito.when(stateRepository.save(Mockito.any(State.class))).thenReturn(state);
        var stateAdded = sut.save(state);
        assertThat(stateAdded).isEqualTo(state);
    }

    @Test
    void whenGetAll_thenStatus200() {
        Mockito.when(stateRepository.findAll())
                .thenReturn(Instancio.ofList(State.class).size(10).create());
        List<State> states = sut.getAll();
        assertThat(states).hasSize(10);
    }

    @Test
    void whenGetValidStateId_thenReturnState() {
        var state = Instancio.create(State.class);
        Mockito.when(stateRepository.findById(state.getId())).thenReturn(Optional.of(state));
        var stateFound = sut.getById(state.getId());
        assertThat(stateFound).isEqualTo(state);
    }

    @Test
    void whenGetInvalidStateId_thenThrowsModelNotFoundException() {
        var stateId = 1L;
        Mockito.when(stateRepository.findById(stateId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> sut.getById(stateId));
    }

    @Test
    void whenDeleteValidState_thenReturnsNothing() {
        var state = Instancio.create(State.class);
        Mockito.when(stateRepository.existsById(state.getId())).thenReturn(true);
        assertDoesNotThrow(() -> sut.remove(state.getId()));
        Mockito.verify(stateRepository, Mockito.times(1)).deleteById(state.getId());
    }

    @Test
    void whenDeleteInvalidState_thenThrowsModelNotFoundException() {
        var stateId = 1L;
        Mockito.when(stateRepository.findById(stateId)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> sut.remove(stateId));
    }
}
