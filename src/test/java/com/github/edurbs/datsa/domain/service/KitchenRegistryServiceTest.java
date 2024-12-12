package com.github.edurbs.datsa.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelNotFoundException;
import com.github.edurbs.datsa.domain.model.Kitchen;

@SpringBootTest
class KitchenRegistryServiceTest {

    @Autowired
    private KitchenRegistryService kitchenRegistryService;

    @Test
    @Transactional
    void shouldAddValidKitchen() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistryService.save(kitchen1);
        kitchen1.setId(kitchenAdded.getId());
        assertThat(kitchenAdded).isEqualTo(kitchen1);
    }

    @Test
    @Transactional
    void shouldFindAll() {
        Instancio.stream(Kitchen.class)
                .limit(10)
                .forEach(kitchenRegistryService::save);
        List<Kitchen> kitchens = kitchenRegistryService.getAll();
        assertThat(kitchens).hasSizeGreaterThan(0);
    }

    @Test
    void shouldFindByIdValidKitchen() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistryService.save(kitchen1);
        assertThat(kitchenRegistryService.getById(kitchenAdded.getId()))
                .isEqualTo(kitchenAdded);
    }

    @Test
    void shouldChangeNameValidKitchen() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistryService.save(kitchen1);
        String name = "new name";
        kitchenAdded.setName(name);
        kitchenRegistryService.save(kitchenAdded);
        assertThat(kitchenRegistryService.getById(kitchenAdded.getId()).getName())
                .isEqualTo(name);
    }

    @Test
    void shouldDeleteValidKitchen() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistryService.save(kitchen1);

        kitchenRegistryService.remove(kitchenAdded.getId());
        var id = kitchenAdded.getId();
        assertThatExceptionOfType(ModelNotFoundException.class)
                .isThrownBy(() -> kitchenRegistryService.getById(id));

    }

    @Test
    void shouldThrowsWhenDeleteInvalidKitchen(){
        assertThrows(ModelNotFoundException.class, () -> kitchenRegistryService.remove(Long.MAX_VALUE));

    }
}
