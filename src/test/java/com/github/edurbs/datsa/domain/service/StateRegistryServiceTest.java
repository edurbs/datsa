package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.StateNotFoundException;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.repository.StateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StateRegistryServiceTest {

    @Mock
    StateRepository stateRepository;

    @InjectMocks
    StateRegistryService sut;
    private State newState;
    private State stateWithId;

    @BeforeEach
    void setup(){
        newState = createNewState();
        stateWithId = createStateWithId();
    }

    @Nested
    class GivenValidState{

        @Test
        void whenSave_thenRepositorySaveIsCalled(){
            sut.save(newState);
            verify(stateRepository).save(any(State.class));
        }

        @Test
        void whenGetAll_thenRepositoryFindAllIsCalled(){
            sut.getAll();
            verify(stateRepository).findAll();
        }

        @Test
        void whenGetById_thenRepositoryFindByIdIsCalled(){
            when(stateRepository.findById(anyLong())).thenReturn(Optional.of(stateWithId));
            sut.getById(1L);
            verify(stateRepository).findById(anyLong());
        }

        @Test
        void whenRemove_thenDeleteAndFlushIsCalled(){
            when(stateRepository.existsById(anyLong())).thenReturn(true);
            sut.remove(1L);
            verify(stateRepository).deleteById(1L);
            verify(stateRepository).flush();
        }

        @Test
        void andStateIsInUse_whenRemove_thenThrowException(){
            when(stateRepository.existsById(anyLong())).thenReturn(true);
            doThrow(DataIntegrityViolationException.class).when(stateRepository).deleteById(anyLong());
            Exception remove = catchException(() -> sut.remove(1L));
            assertThat(remove).isInstanceOf(ModelInUseException.class);
        }

    }

    @Nested
    class GivenNonExistentState{
        @Test
        void whenGetById_thenThrowException(){
            when(stateRepository.findById(anyLong())).thenReturn(Optional.empty());
            Throwable getById = catchException(() -> sut.getById(1L));
            assertThat(getById).isInstanceOf(StateNotFoundException.class);
        }

        @Test
        void whenRemove_thenThrowException(){
            when(stateRepository.existsById(anyLong())).thenReturn(false);
            Throwable remove = catchException(() -> sut.remove(1L));
            assertThat(remove).isInstanceOf(StateNotFoundException.class);
        }
    }


    private State createStateWithId() {
        State state = new State();
        state.setName("State name");
        state.setId(1L);
        return state;
    }

    private State createNewState(){
        State state = createStateWithId();
        state.setId(null);
        return state;
    }
}