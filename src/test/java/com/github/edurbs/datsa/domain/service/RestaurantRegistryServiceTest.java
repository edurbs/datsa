package com.github.edurbs.datsa.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.infra.repository.RestaurantRepository;

@SpringBootTest
class RestaurantRegistryServiceTest {

    @Autowired
    private RestaurantRegistryService sut;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private KitchenRegistryService kitchenRegistryServiceMock;

    @Mock
    private Restaurant restaurantMock = Instancio.create(Restaurant.class);
    @Mock
    private Kitchen kitchenMock = Instancio.create(Kitchen.class);

    @Test
    void whenAddValidRestaurant_thenReturnRestaurant() {
        Mockito.when(restaurantMock.getKitchen()).thenReturn(kitchenMock);
        Mockito.when(kitchenRegistryServiceMock.getById(kitchenMock.getId())).thenReturn(kitchenMock);
        Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(restaurantMock);
        var restaurantAdded = sut.save(restaurantMock);
        assertThat(restaurantAdded).isEqualTo(restaurantMock);
    }

    @Test
    void whenAddRestaurantWithNullKitchen_thenThrowsModelNotFoundException() {
        Mockito.when(restaurantMock.getKitchen()).thenReturn(null);
        Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(restaurantMock);
        assertThatExceptionOfType(ModelValidationException.class)
                .isThrownBy(() -> sut.save(restaurantMock));
    }

    @Test
    void whenAddRestaurantWithKitchenWithoutId_thenThrowsModelNotFoundException() {
        Mockito.when(kitchenMock.getId()).thenReturn(null);
        Mockito.when(restaurantMock.getKitchen()).thenReturn(kitchenMock);
        Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(restaurantMock);
        assertThatExceptionOfType(ModelValidationException.class)
                .isThrownBy(() -> sut.save(restaurantMock));
    }

    @Test
    void whenAddRestaurantWithKitchenInexistent_thenThrowsModelNotFoundException() {
        Mockito.when(kitchenMock.getId()).thenReturn(1L);
        Mockito.doThrow(new ModelNotFoundException()).when(kitchenRegistryServiceMock).getById(1L);
        Mockito.when(restaurantMock.getKitchen()).thenReturn(kitchenMock);
        Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(restaurantMock);
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> sut.save(restaurantMock));
    }

    @Test
    void whenGetAll_thenStatus200() {
        Mockito.when(restaurantRepository.findAll())
                .thenReturn(Instancio.ofList(Restaurant.class).size(10).create());
        List<Restaurant> restaurants = sut.getAll();
        assertThat(restaurants).hasSize(10);
    }

    @Test
    void whenGetValidRestaurantId_thenReturnRestaurant() {
        Mockito.when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantMock));
        assertThat(sut.getById(1L))
                .isEqualTo(restaurantMock);
    }

    @Test
    void whenGetInvalidRestaurantId_thenThrowsModelNotFoundException() {
        Mockito.when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> sut.getById(999L));
    }

    @Test
    void whenDeleteValidRestaurant_thenReturnsNothing() {
        Mockito.when(restaurantRepository.existsById(1L)).thenReturn(true);
        assertDoesNotThrow(() -> sut.remove(1L));
        Mockito.verify(restaurantRepository).deleteById(1L);
    }

    @Test
    void whenDeleteInvalidRestaurant_thenThrowsModelNotFoundException() {
        Mockito.when(restaurantRepository.existsById(1L)).thenReturn(false);
        assertThrows(ModelNotFoundException.class, () -> sut.remove(1L));
    }
}
