package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.CityNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityRegistryServiceTest {

    @Mock
    CityRepository cityRepository;

    @Mock
    StateRegistryService stateRegistryService;

    @InjectMocks
    CityRegistryService sut;

    private City newCity;
    private City cityWithId;
    private State stateWithId;

    @BeforeEach
    void setup() {
        stateWithId = createStateWithId();
        newCity = createNewCity();
        cityWithId = createCityWithId();
    }

    @Nested
    class GivenValidCity {

        @Test
        void whenSaveWithValidData_thenRepositorySaveIsCalled() {
            // given
            when(stateRegistryService.getById(anyLong())).thenReturn(stateWithId);

            // when
            sut.save(newCity);

            // then
            verify(cityRepository).save(any(City.class));
        }

        @Test
        void whenSave_thenStateNameIsUpdated() {
            // given
            City city = createNewCity();
            State stateExpected = city.getState();
            when(stateRegistryService.getById(anyLong())).thenReturn(stateExpected);
            ArgumentCaptor<City> cityArgumentCaptor = ArgumentCaptor.forClass(City.class);

            // when
            sut.save(newCity);

            // then
            verify(cityRepository).save(cityArgumentCaptor.capture());
            City cityToSave = cityArgumentCaptor.getValue();
            State stateToSave = cityToSave.getState();
            assertThat(stateToSave).isEqualTo(stateExpected);
        }

        @Test
        void whenGetAll_thenRepositoryFindAllIsCalled() {
            sut.getAll();
            verify(cityRepository).findAll();
        }

        @Test
        void whenGetById_thenRepositoryFindByIdIsCalled() {
            when(cityRepository.findById(anyLong())).thenReturn(Optional.of(cityWithId));
            sut.getById(1L);
            verify(cityRepository).findById(anyLong());
        }

        @Test
        void whenRemove_thenDeleteAndFlushIsCalled() {
            when(cityRepository.existsById(anyLong())).thenReturn(true);
            when(cityRepository.countByStateId(anyLong())).thenReturn(0L);
            sut.remove(1L);
            verify(cityRepository).deleteById(1L);
            verify(cityRepository).flush();
        }

        @Test
        void andCityIsInUse_whenRemove_thenThrowException() {
            when(cityRepository.existsById(anyLong())).thenReturn(true);
            when(cityRepository.countByStateId(anyLong())).thenReturn(1L);
            Exception remove = catchException(() -> sut.remove(1L));
            assertThat(remove).isInstanceOf(ModelInUseException.class);
        }
    }

    @Nested
    class GivenInvalidCity {

        @Test
        void whenSaveWithNullState_thenThrowException() {
            // given
            City city = new City();
            city.setState(null);

            // when
            Exception save = catchException(() -> sut.save(city));

            // then
            assertThat(save).isInstanceOf(ModelValidationException.class)
                    .hasMessage("State not informed");
        }

        @Test
        void whenSaveWithStateWithoutId_thenThrowException() {
            // given
            City city = createCityWithId();
            city.getState().setId(null);

            // when
            Exception save = catchException(() -> sut.save(city));

            // then
            assertThat(save).isInstanceOf(ModelValidationException.class)
                    .hasMessage("State id not informed");
        }
    }

    @Nested
    class GivenNonExistentCity {

        @Test
        void whenGetById_thenThrowException() {
            when(cityRepository.findById(anyLong())).thenReturn(Optional.empty());
            Throwable getById = catchException(() -> sut.getById(1L));
            assertThat(getById).isInstanceOf(CityNotFoundException.class);
        }

        @Test
        void whenRemove_thenThrowException() {
            when(cityRepository.existsById(anyLong())).thenReturn(false);
            Throwable remove = catchException(() -> sut.remove(1L));
            assertThat(remove).isInstanceOf(CityNotFoundException.class);
        }
    }

    private City createCityWithId() {
        City city = new City();
        city.setName("City name");
        city.setId(1L);
        city.setState(stateWithId);
        return city;
    }

    private City createNewCity() {
        City city = createCityWithId();
        city.setId(null);
        return city;
    }

    private State createStateWithId() {
        State state = new State();
        state.setName("State name");
        state.setId(1L);
        return state;
    }
}