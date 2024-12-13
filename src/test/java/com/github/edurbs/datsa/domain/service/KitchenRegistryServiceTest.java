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
import org.springframework.dao.DataIntegrityViolationException;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Kitchen;
import com.github.edurbs.datsa.domain.repository.KitchenRepository;

@SpringBootTest
class KitchenRegistryServiceTest {

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @MockBean
    private KitchenRepository kitchenRepository;

    @Mock
    private Kitchen kitchen = Instancio.create(Kitchen.class);

    @Test
    void whenAddValidKitchen_thenReturnKitchen() {

        Mockito.when(kitchenRepository.save(Mockito.any(Kitchen.class))).thenReturn(kitchen);
        var kitchenAdded = kitchenRegistryService.save(kitchen);
        assertThat(kitchenAdded).isEqualTo(kitchen);
    }

    @Test
    void whenGetAll_thenReturnKitchens() {

        Mockito.when(kitchenRepository.findAll())
                .thenReturn(Instancio.ofList(Kitchen.class).size(10).create());
        List<Kitchen> kitchens = kitchenRegistryService.getAll();
        assertThat(kitchens).hasSize(10);
    }

    @Test
    void whenGetValidKitchenId_thenReturnKitchen() {
        Mockito.when(kitchenRepository.findById(1L)).thenReturn(Optional.of(kitchen));
        assertThat(kitchenRegistryService.getById(1L))
                .isEqualTo(kitchen);
    }

    @Test
    void whenGetInvalidKitchenId_thenThrowsModelNotFoundException() {
        Mockito.when(kitchenRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> kitchenRegistryService.getById(999L));
    }

    @Test
    void whenDeleteValidKitchen_thenReturnsNothing() {
        Mockito.when(kitchenRepository.existsById(1L)).thenReturn(true);
        kitchenRegistryService.remove(1L);
        Mockito.verify(kitchenRepository).deleteById(1L);
    }

    @Test
    void whenDeleteInvalidKitchen_thenThrowsModelNotFoundException() {
        Mockito.when(kitchenRepository.existsById(1L)).thenReturn(false);
        assertThrows(ModelNotFoundException.class, () -> kitchenRegistryService.remove(Long.MAX_VALUE));

    }

    @Test
    void whenDeleteInvalidKitchen_thenThrowsModelNotFoundExceptionWithMessage() {
        Mockito.when(kitchenRepository.existsById(1L)).thenReturn(false);
        assertThrows(ModelNotFoundException.class, () -> kitchenRegistryService.remove(1L),
                "Kitchen id 1 does not exists");
    }

    @Test
    void whenDeleteModelInUse_thenThrowsModelInUseException() {
        Mockito.when(kitchenRepository.existsById(1L)).thenReturn(true);
        Mockito.doThrow(new DataIntegrityViolationException("msg")).when(kitchenRepository).deleteById(1L);
        assertThatExceptionOfType(ModelInUseException.class)
                .isThrownBy(() -> kitchenRegistryService.remove(1L));
    }
}
