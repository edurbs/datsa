package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RoorEntryPointController {

    @Autowired
    private LinksAdder linksAdder;

    @GetMapping
    public RootEntryPointModel root(){
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();
        rootEntryPointModel.add(linksAdder.toKitchens("kitchens"));
        rootEntryPointModel.add(linksAdder.toOrders("orders"));
        rootEntryPointModel.add(linksAdder.toRestaurants("restaurants"));
        rootEntryPointModel.add(linksAdder.toGroups("groups"));
        rootEntryPointModel.add(linksAdder.toUsers("users"));
        rootEntryPointModel.add(linksAdder.toPermissions("permissions"));
        rootEntryPointModel.add(linksAdder.toPaymentMethods("payment-methods"));
        rootEntryPointModel.add(linksAdder.toStates("states"));
        rootEntryPointModel.add(linksAdder.toCities("cities"));
        rootEntryPointModel.add(linksAdder.toStatistics("statistics"));

        return rootEntryPointModel;
    }

    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{

    }
}
