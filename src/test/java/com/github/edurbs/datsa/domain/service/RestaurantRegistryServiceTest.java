package com.github.edurbs.datsa.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
import com.github.edurbs.datsa.domain.model.Restaurant;
import com.github.edurbs.datsa.domain.repository.RestaurantRepository;

@SpringBootTest
class RestaurantRegistryServiceTest {

    @Autowired
    private RestaurantRegistryService restaurantRegistryService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Mock
    private Restaurant restaurant = Instancio.create(Restaurant.class);

    @Test
    void whenAddValidRestaurant_thenReturnRestaurant() {
        Mockito.when(restaurantRepository.save(Mockito.any(Restaurant.class))).thenReturn(restaurant);
        var restaurantAdded = restaurantRegistryService.save(restaurant);
        assertThat(restaurantAdded).isEqualTo(restaurant);
    }

    @Test
    void whenGetAll_thenStatus200() {
        Mockito.when(restaurantRepository.findAll())
                .thenReturn(Instancio.ofList(Restaurant.class).size(10).create());
        List<Restaurant> restaurants = restaurantRegistryService.getAll();
        assertThat(restaurants).hasSize(10);
    }

    @Test
    void whenGetValidRestaurantId_thenReturnRestaurant() {
        Mockito.when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        assertThat(restaurantRegistryService.getById(1L))
                .isEqualTo(restaurant);
    }

    @Test
    void whenGetInvalidRestaurantId_thenThrowsModelNotFoundException() {
        Mockito.when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> restaurantRegistryService.getById(999L));
    }

    @Test
    void whenDeleteValidRestaurant_thenReturnsNothing() {
        Mockito.when(restaurantRepository.existsById(1L)).thenReturn(true);
        restaurantRegistryService.remove(1L);
        Mockito.verify(restaurantRepository).deleteById(1L);
    }

    @Test
    void whenDeleteInvalidRestaurant_thenThrowsModelNotFoundException() {
        Mockito.when(restaurantRepository.existsById(1L)).thenReturn(false);
        assertThrows(ModelNotFoundException.class, () -> restaurantRegistryService.remove(1L));
    }
}
