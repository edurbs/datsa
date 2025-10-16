package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.City;
import com.github.edurbs.datsa.domain.model.State;
import com.github.edurbs.datsa.domain.repository.CityRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CityRegistryServiceTest {

    @Autowired
    private CityRegistryService sut;

    @MockitoBean
    private CityRepository cityRepository;

    @MockitoBean
    private StateRegistryService stateRegistryService;

    @Mock
    private City cityMock = Instancio.create(City.class);

    @Mock
    private State stateMock = Instancio.create(State.class);

    @Test
    void whenAddValidCity_thenReturnCity(){
        Mockito.when(cityMock.getState()).thenReturn(stateMock);
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenReturn(cityMock);
        Mockito.when(stateRegistryService.getById(Mockito.any(Long.class))).thenReturn(stateMock);
        assertThat(sut.save(cityMock)).isEqualTo(cityMock);
    }

    @Test
    void whenGetAll_thenReturnCities(){
        Mockito.when(cityRepository.findAll()).thenReturn(Instancio.ofList(City.class).size(10).create());
        List<City> cities = sut.getAll();
        assertThat(cities).hasSize(10);
    }

    @Test
    void whenGetValidCityId_thenReturnCity(){
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.of(cityMock));
        assertThat(sut.getById(1L))
                .isEqualTo(cityMock);
    }

    @Test
    void whenGetInvalidCityId_thenThrowsModelNotFoundException(){
        Mockito.when(cityRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> sut.getById(1L));
    }

    @Test
    void whenDeleteValidCity_thenReturnsNothing(){
        Mockito.when(cityRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> sut.remove(1L));
        Mockito.verify(cityRepository).deleteById(1L);}

    @Test
    void whenDeleteInvalidCity_thenThrowsModelNotFoundException(){
        Mockito.when(cityRepository.existsById(1L)).thenReturn(false);
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> sut.remove(1L));
    }

    @Test
    void whenDeleteModelInUse_thenThrowsModelInUseException(){
        Mockito.when(cityRepository.existsById(1L)).thenReturn(true);
        Mockito.when(cityRepository.countByStateId(1L)).thenReturn(1L);
        assertThatExceptionOfType(ModelInUseException.class)
                .isThrownBy(() -> sut.remove(1L));
    }
}
