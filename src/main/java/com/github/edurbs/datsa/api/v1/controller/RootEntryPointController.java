package com.github.edurbs.datsa.api.v1.controller;

import com.github.edurbs.datsa.api.v1.LinksAdder;
import com.github.edurbs.datsa.api.v1.openapi.controller.RootEntryPointControllerOpenApi;
import com.github.edurbs.datsa.core.security.MySecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RootEntryPointController implements RootEntryPointControllerOpenApi {

    @Autowired
    private LinksAdder linksAdder;

    @Autowired
    private MySecurity mySecurity;

    @GetMapping
    @Override
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();
        if (this.mySecurity.canConsultKitchens()) {
            rootEntryPointModel.add(linksAdder.toKitchens("kitchens"));
        }
        if (this.mySecurity.canSearchWithFilter()) {
            rootEntryPointModel.add(linksAdder.toOrders("orders"));
        }
        if (this.mySecurity.canConsultRestaurants()) {
            rootEntryPointModel.add(linksAdder.toRestaurants("restaurants"));
        }
        if (this.mySecurity.canConsultUsersGroupsPermissions()) {
            rootEntryPointModel.add(linksAdder.toGroups("groups"));
            rootEntryPointModel.add(linksAdder.toUsers("users"));
            rootEntryPointModel.add(linksAdder.toPermissions("permissions"));
        }
        if (this.mySecurity.canConsultPaymentMethods()) {
            rootEntryPointModel.add(linksAdder.toPaymentMethods("payment-methods"));
        }
        if (this.mySecurity.canConsultStates()) {
            rootEntryPointModel.add(linksAdder.toStates("states"));
        }
        if (this.mySecurity.canConsultCities()) {
            rootEntryPointModel.add(linksAdder.toCities("cities"));
        }
        if (this.mySecurity.canConsultStatistics()) {
            rootEntryPointModel.add(linksAdder.toStatistics("statistics"));
        }

        return rootEntryPointModel;
    }

}
