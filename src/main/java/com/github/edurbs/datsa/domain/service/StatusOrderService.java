package com.github.edurbs.datsa.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.model.Order;

@Service
public class StatusOrderService {

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    OrderRegistryService orderRegistryService;

    @Transactional
    public void confirm(String uuid){
        Order order = orderRegistryService.getById(uuid);
        order.confirm();

        var message = EmailSenderService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order confirmed")
                .body("orderConfirmed.html")
                .recipient(order.getUser().getEmail())
                .model("order", order)
                .build();
        emailSenderService.send(message);
    }

    @Transactional
    public void delivery(String uuid){
        Order order = orderRegistryService.getById(uuid);
        order.delivery();
    }

    @Transactional
    public void cancel(String uuid){
        Order order = orderRegistryService.getById(uuid);
        order.cancel();
    }

}
