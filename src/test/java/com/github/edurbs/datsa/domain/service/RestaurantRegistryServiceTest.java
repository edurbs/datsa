package com.github.edurbs.datsa.domain.service;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.RestaurantNotFoundException;
import com.github.edurbs.datsa.domain.model.*;
import com.github.edurbs.datsa.domain.repository.ProductRepository;
import com.github.edurbs.datsa.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantRegistryServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    KitchenRegistryService kitchenRegistryService;

    @Mock
    CityRegistryService cityRegistryService;

    @Mock
    PaymentMethodRegistryService paymentMethodRegistryService;

    @Mock
    UserRegistryService userRegistryService;

    @InjectMocks
    RestaurantRegistryService sut;

    private Restaurant newRestaurant;
    private Restaurant restaurantWithId;
    private Kitchen kitchenWithId;
    private City cityWithId;

    @BeforeEach
    void setup() {
        kitchenWithId = createKitchenWithId();
        cityWithId = createCityWithId();
        newRestaurant = createNewRestaurant();
        restaurantWithId = createRestaurantWithId();
    }

    @Nested
    class GivenValidRestaurant {

        @Test
        void whenSaveWithValidData_thenRepositorySaveIsCalled() {
            // given
            when(kitchenRegistryService.getById(anyLong())).thenReturn(kitchenWithId);
            when(cityRegistryService.getById(anyLong())).thenReturn(cityWithId);
            when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurantWithId);

            // when
            sut.save(newRestaurant);

            // then
            verify(restaurantRepository).save(any(Restaurant.class));
        }

        @Test
        void whenSave_thenKitchenIsUpdated() {
            // given
            when(kitchenRegistryService.getById(anyLong())).thenReturn(kitchenWithId);
            when(cityRegistryService.getById(anyLong())).thenReturn(cityWithId);
            when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurantWithId);

            // when
            Restaurant savedRestaurant = sut.save(newRestaurant);

            // then
            assertThat(savedRestaurant.getKitchen().getName()).isEqualTo(kitchenWithId.getName());
            verify(kitchenRegistryService).getById(anyLong());
        }

        @Test
        void whenSaveWithAddress_thenCityIsUpdated() {
            // given
            when(kitchenRegistryService.getById(anyLong())).thenReturn(kitchenWithId);
            when(cityRegistryService.getById(anyLong())).thenReturn(cityWithId);
            when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurantWithId);

            // when
            Restaurant savedRestaurant = sut.save(newRestaurant);

            // then
            assertThat(savedRestaurant.getAddress().getCity().getName()).isEqualTo(cityWithId.getName());
            verify(cityRegistryService).getById(anyLong());
        }

        @Test
        void whenGetAll_thenRepositoryFindAllIsCalled() {
            sut.getAll();
            verify(restaurantRepository).findAll();
        }

        @Test
        void whenGetById_thenRepositoryFindByIdIsCalled() {
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));
            sut.getById(1L);
            verify(restaurantRepository).findById(anyLong());
        }

        @Test
        void whenRemove_thenDeleteAndFlushIsCalled() {
            when(restaurantRepository.existsById(anyLong())).thenReturn(true);
            sut.remove(1L);
            verify(restaurantRepository).deleteById(1L);
            verify(restaurantRepository).flush();
        }

        @Test
        void andRestaurantIsInUse_whenRemove_thenThrowException() {
            when(restaurantRepository.existsById(anyLong())).thenReturn(true);
            doThrow(DataIntegrityViolationException.class).when(restaurantRepository).deleteById(anyLong());
            Exception remove = catchException(() -> sut.remove(1L));
            assertThat(remove).isInstanceOf(ModelInUseException.class);
        }

        @Test
        void whenActivate_thenRestaurantIsActive() {
            // given
            Restaurant inactiveRestaurant = createRestaurantWithId();
            inactiveRestaurant.inactivate();
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(inactiveRestaurant));

            // when
            sut.activate(1L);

            // then
            assertThat(inactiveRestaurant.isActive()).isTrue();
        }

        @Test
        void whenInactivate_thenRestaurantIsInactive() {
            // given
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));

            // when
            sut.inactivate(1L);

            // then
            assertThat(restaurantWithId.isInactive()).isTrue();
        }

        @Test
        void whenOpen_thenRestaurantIsOpen() {
            // given
            Restaurant closedRestaurant = createRestaurantWithId();
            closedRestaurant.close();
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(closedRestaurant));

            // when
            sut.open(1L);

            // then
            assertThat(closedRestaurant.isOpen()).isTrue();
        }

        @Test
        void whenClose_thenRestaurantIsClosed() {
            // given
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));

            // when
            sut.close(1L);

            // then
            assertThat(restaurantWithId.isClosed()).isTrue();
        }

        @Test
        void whenActivations_thenAllRestaurantsInListAreActivated() {
            // given
            List<Long> restaurantIds = Arrays.asList(1L, 2L, 3L);
            Restaurant inactiveRestaurant1 = createRestaurantWithId();
            inactiveRestaurant1.setId(1L);
            inactiveRestaurant1.inactivate();
            
            Restaurant inactiveRestaurant2 = createRestaurantWithId();
            inactiveRestaurant2.setId(2L);
            inactiveRestaurant2.inactivate();
            
            Restaurant inactiveRestaurant3 = createRestaurantWithId();
            inactiveRestaurant3.setId(3L);
            inactiveRestaurant3.inactivate();
            
            when(restaurantRepository.findById(1L)).thenReturn(Optional.of(inactiveRestaurant1));
            when(restaurantRepository.findById(2L)).thenReturn(Optional.of(inactiveRestaurant2));
            when(restaurantRepository.findById(3L)).thenReturn(Optional.of(inactiveRestaurant3));

            // when
            sut.activations(restaurantIds);

            // then
            assertThat(inactiveRestaurant1.isActive()).isTrue();
            assertThat(inactiveRestaurant2.isActive()).isTrue();
            assertThat(inactiveRestaurant3.isActive()).isTrue();
            verify(restaurantRepository, times(3)).findById(anyLong());
        }

        @Test
        void whenInactivations_thenAllRestaurantsInListAreInactivated() {
            // given
            List<Long> restaurantIds = Arrays.asList(1L, 2L, 3L);
            Restaurant activeRestaurant1 = createRestaurantWithId();
            activeRestaurant1.setId(1L);
            
            Restaurant activeRestaurant2 = createRestaurantWithId();
            activeRestaurant2.setId(2L);
            
            Restaurant activeRestaurant3 = createRestaurantWithId();
            activeRestaurant3.setId(3L);
            
            when(restaurantRepository.findById(1L)).thenReturn(Optional.of(activeRestaurant1));
            when(restaurantRepository.findById(2L)).thenReturn(Optional.of(activeRestaurant2));
            when(restaurantRepository.findById(3L)).thenReturn(Optional.of(activeRestaurant3));

            // when
            sut.inactivations(restaurantIds);

            // then
            assertThat(activeRestaurant1.isInactive()).isTrue();
            assertThat(activeRestaurant2.isInactive()).isTrue();
            assertThat(activeRestaurant3.isInactive()).isTrue();
            verify(restaurantRepository, times(3)).findById(anyLong());
        }

        @Test
        void whenAssociateUser_thenUserIsAddedToRestaurant() {
            // given
            MyUser user = createUserWithId();
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));
            when(userRegistryService.getById(anyLong())).thenReturn(user);

            // when
            sut.associateUser(1L, 1L);

            // then
            verify(restaurantRepository).findById(1L);
            verify(userRegistryService).getById(1L);
            assertThat(restaurantWithId.getUsers()).contains(user);
        }

        @Test
        void whenDisassociateUser_thenUserIsRemovedFromRestaurant() {
            // given
            MyUser user = createUserWithId();
            restaurantWithId.addUser(user);
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));
            when(userRegistryService.getById(anyLong())).thenReturn(user);

            // when
            sut.disassociateUser(1L, 1L);

            // then
            verify(restaurantRepository).findById(1L);
            verify(userRegistryService).getById(1L);
            assertThat(restaurantWithId.getUsers()).doesNotContain(user);
        }

        @Test
        void whenGetAllActiveProducts_thenReturnActiveProducts() {
            // given
            Product product1 = createProductWithId();
            Product product2 = createProductWithId();
            product2.setId(2L);
            product1.setActive(true);
            product2.setActive(true);
            
            when(productRepository.findByActiveTrueAndRestaurantId(anyLong()))
                .thenReturn(Arrays.asList(product1, product2));

            // when
            Set<Product> activeProducts = sut.getAllActiveProducts(1L);

            // then
            assertThat(activeProducts).containsExactlyInAnyOrder(product1, product2);
            verify(productRepository).findByActiveTrueAndRestaurantId(1L);
        }

        @Test
        void whenGetAllProducts_thenReturnAllProducts() {
            // given
            Product product1 = createProductWithId();
            Product product2 = createProductWithId();
            product2.setId(2L);
            
            Restaurant restaurantWithProducts = createRestaurantWithId();
            restaurantWithProducts.getProducts().add(product1);
            restaurantWithProducts.getProducts().add(product2);
            
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithProducts));

            // when
            Set<Product> allProducts = sut.getAllProducts(1L);

            // then
            assertThat(allProducts).isEqualTo(restaurantWithProducts.getProducts());
            verify(restaurantRepository).findById(1L);
        }

        @Test
        void whenGetProduct_thenReturnSpecificProduct() {
            // given
            Product product = createProductWithId();
            restaurantWithId.getProducts().add(product);
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));

            // when
            Product retrievedProduct = sut.getProduct(1L, 1L);

            // then
            assertThat(retrievedProduct).isEqualTo(product);
            verify(restaurantRepository).findById(1L);
        }

        @Test
        void whenAssociatePaymentMethod_thenPaymentMethodIsAddedToRestaurant() {
            // given
            PaymentMethod paymentMethod = createPaymentMethodWithId();
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));
            when(paymentMethodRegistryService.getById(anyLong())).thenReturn(paymentMethod);

            // when
            sut.associatePaymentMethod(1L, 1L);

            // then
            verify(restaurantRepository).findById(1L);
            verify(paymentMethodRegistryService).getById(1L);
            assertThat(restaurantWithId.getPaymentMethods()).contains(paymentMethod);
        }

        @Test
        void whenDisassociatePaymentMethod_thenPaymentMethodIsRemovedFromRestaurant() {
            // given
            PaymentMethod paymentMethod = createPaymentMethodWithId();
            restaurantWithId.addPaymentMethod(paymentMethod);
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));
            when(paymentMethodRegistryService.getById(anyLong())).thenReturn(paymentMethod);

            // when
            sut.disassociatePaymentMethod(1L, 1L);

            // then
            verify(restaurantRepository).findById(1L);
            verify(paymentMethodRegistryService).getById(1L);
            assertThat(restaurantWithId.getPaymentMethods()).doesNotContain(paymentMethod);
        }
    }

    @Nested
    class GivenNonExistentRestaurant {

        @Test
        void whenGetById_thenThrowException() {
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());
            Throwable getById = catchException(() -> sut.getById(1L));
            assertThat(getById).isInstanceOf(RestaurantNotFoundException.class);
        }

        @Test
        void whenRemove_thenThrowException() {
            when(restaurantRepository.existsById(anyLong())).thenReturn(false);
            Throwable remove = catchException(() -> sut.remove(1L));
            assertThat(remove).isInstanceOf(RestaurantNotFoundException.class);
        }
    }

    @Nested
    class GivenInvalidRestaurant {

        @Test
        void whenActivateAlreadyActiveRestaurant_thenThrowException() {
            // given
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));

            // when
            Exception activate = catchException(() -> sut.activate(1L));

            // then
            assertThat(activate).isInstanceOf(ModelValidationException.class)
                    .hasMessage("Restaurant id %d already active".formatted(1L));
        }

        @Test
        void whenInactivateAlreadyInactiveRestaurant_thenThrowException() {
            // given
            Restaurant inactiveRestaurant = createRestaurantWithId();
            inactiveRestaurant.inactivate();
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(inactiveRestaurant));

            // when
            Exception inactivate = catchException(() -> sut.inactivate(1L));

            // then
            assertThat(inactivate).isInstanceOf(ModelValidationException.class)
                    .hasMessage("Restaurant id %d already inactive".formatted(1L));
        }

        @Test
        void whenOpenAlreadyOpenRestaurant_thenThrowException() {
            // given
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurantWithId));

            // when
            Exception open = catchException(() -> sut.open(1L));

            // then
            assertThat(open).isInstanceOf(ModelValidationException.class)
                    .hasMessage("Restaurant id %d is already opened.".formatted(1L));
        }

        @Test
        void whenCloseAlreadyClosedRestaurant_thenThrowException() {
            // given
            Restaurant closedRestaurant = createRestaurantWithId();
            closedRestaurant.close();
            when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(closedRestaurant));

            // when
            Exception close = catchException(() -> sut.close(1L));

            // then
            assertThat(close).isInstanceOf(ModelValidationException.class);
        }
    }

    private Restaurant createRestaurantWithId() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Restaurant name");
        restaurant.setId(1L);
        restaurant.setShippingFee(BigDecimal.TEN);
        restaurant.setKitchen(kitchenWithId);

        Address address = new Address();
        address.setCity(cityWithId);
        restaurant.setAddress(address);

        return restaurant;
    }

    private Restaurant createNewRestaurant() {
        Restaurant restaurant = createRestaurantWithId();
        restaurant.setId(null);
        return restaurant;
    }

    private Kitchen createKitchenWithId() {
        Kitchen kitchen = new Kitchen();
        kitchen.setName("Kitchen name");
        kitchen.setId(1L);
        return kitchen;
    }

    private City createCityWithId() {
        City city = new City();
        city.setName("City name");
        city.setId(1L);
        return city;
    }

    private PaymentMethod createPaymentMethodWithId() {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        paymentMethod.setDescription("Credit Card");
        return paymentMethod;
    }

    private MyUser createUserWithId() {
        MyUser user = new MyUser();
        user.setId(1L);
        user.setName("User Name");
        user.setEmail("user@example.com");
        return user;
    }

    private Product createProductWithId() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product Name");
        product.setDescription("Product Description");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setActive(true);
        product.setRestaurant(restaurantWithId);
        return product;
    }
}