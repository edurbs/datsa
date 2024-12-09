package com.github.edurbs.datsa.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.model.Kitchen;

@SpringBootTest
class KitchenRegistryTest {

    @Autowired
    private KitchenRegistry kitchenRegistry;

    @Test
    @Transactional
    void testAdd() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistry.save(kitchen1);
        kitchen1.setId(kitchenAdded.getId());
        assertThat(kitchenAdded).isEqualTo(kitchen1);
    }

    @Test
    @Transactional
    void testFindAll() {
        Instancio.stream(Kitchen.class)
                .limit(10)
                .forEach(kitchenRegistry::save);
        List<Kitchen> kitchens = kitchenRegistry.findAll();
        assertThat(kitchens).hasSize(10);
    }

    @Test
    void testFindById() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistry.save(kitchen1);
        assertThat(kitchenRegistry.findById(kitchenAdded.getId())).isEqualTo(kitchenAdded);
    }

    @Test
    void testChangeEntity() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistry.save(kitchen1);
        kitchenAdded.setName("new name");
        kitchenRegistry.save(kitchenAdded);
        assertThat(kitchenRegistry.findById(kitchenAdded.getId()).getName()).isEqualTo("new name");
    }

    @Test
    void testDelete() {
        var kitchen1 = Instancio.create(Kitchen.class);
        var kitchenAdded = kitchenRegistry.save(kitchen1);
        var kitchen2 = Instancio.create(Kitchen.class);
        kitchenRegistry.save(kitchen2);
        kitchenRegistry.delete(kitchenAdded.getId());
        assertThat(kitchenRegistry.findById(kitchenAdded.getId())).isNull();
    }
}
