package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.dto.output.PaymentMethodOutput;
import com.github.edurbs.datsa.api.v1.mapper.PaymentMethodMapper;
import com.github.edurbs.datsa.api.v1.openapi.controller.RestaurantPaymentMethodControllerOpenApi;
import com.github.edurbs.datsa.core.security.CheckSecurity;
import com.github.edurbs.datsa.core.security.MySecurity;
import com.github.edurbs.datsa.domain.service.RestaurantRegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/restaurants/{restaurantId}/payment-methods")
@RequiredArgsConstructor
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodControllerOpenApi {

    private final RestaurantRegistryService restaurantRegistryService;

    private final PaymentMethodMapper paymentMethodMapper;

    private final LinksAdder linksAdder;
    private final MySecurity mySecurity;

    @CheckSecurity.Restaurants.CanConsult
    @GetMapping
    @Override
    public CollectionModel<PaymentMethodOutput> listAll(@PathVariable Long restaurantId) {
        var restaurant = restaurantRegistryService.getById(restaurantId);
        CollectionModel<PaymentMethodOutput> collectionModel = paymentMethodMapper
                .toCollectionModel(restaurant.getPaymentMethods()).removeLinks();
        collectionModel.add(linksAdder.toRestaurantPaymentMethods(restaurantId));
        if (this.mySecurity.canEditAndManageRestaurant(restaurantId)) {
            collectionModel.add(linksAdder.associatePaymentMethod(restaurantId, "associate"));
            collectionModel.getContent().forEach(paymentMethod ->
                paymentMethod.add(
                        linksAdder.toDisassociatePaymentMethod(restaurantId, paymentMethod.getId(), "disassociate"))
            );
        }
        return collectionModel;

    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantRegistryService.disassociatePaymentMethod(restaurantId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurants.CanEditAndManage
    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantRegistryService.associatePaymentMethod(restaurantId, paymentMethodId);
        return ResponseEntity.noContent().build();
    }

}
