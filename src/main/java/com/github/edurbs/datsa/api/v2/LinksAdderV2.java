package com.github.edurbs.datsa.api.v2;

import com.github.edurbs.datsa.api.v2.controller.CityControllerV2;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LinksAdderV2 {

    public Link toCities() {
        return linkTo(methodOn(CityControllerV2.class).listAll()).withSelfRel();
    }

    public Link toCities(String rel) {
        return linkTo(CityControllerV2.class).withRel(rel);
    }


}
