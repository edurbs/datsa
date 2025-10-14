package com.github.edurbs.datsa.api.v1.openapi.controller;

import com.github.edurbs.datsa.api.v1.dto.input.RestaurantInput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantNameOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantOutput;
import com.github.edurbs.datsa.api.v1.dto.output.RestaurantSummaryOutput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@SecurityRequirement(name="security_auth")
@Tag(name = "Restaurants", description = "Restaurants registry")
public interface RestaurantControllerOpenApi {
    CollectionModel<RestaurantSummaryOutput> listAll();

    CollectionModel<RestaurantNameOutput> listAllOnlyName();

    RestaurantOutput getById(@PathVariable Long restaurantId);

    ResponseEntity<RestaurantOutput> add(@RequestBody @Valid RestaurantInput restaurantInput);

    RestaurantOutput alter(@PathVariable Long restaurantId,
                           @RequestBody @Valid RestaurantInput restaurantInput);

    void delete(@PathVariable Long restaurantId);

    ResponseEntity<Void> activate(@PathVariable Long restaurantId);

    ResponseEntity<Void> activations(@RequestBody List<Long> restaurantIds);

    ResponseEntity<Void> inactivate(@PathVariable Long restaurantId);

    ResponseEntity<Void> inactivations(@RequestBody List<Long> restaurantIds);

    ResponseEntity<Void> open(@PathVariable Long restaurantId);

    ResponseEntity<Void> close(@PathVariable Long restaurantId);
}
