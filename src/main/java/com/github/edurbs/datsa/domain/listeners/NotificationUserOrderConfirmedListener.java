package com.github.edurbs.datsa.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.edurbs.datsa.domain.event.OrderConfirmedEvent;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.service.EmailSenderService;

@Component
public class NotificationUserOrderConfirmedListener {

    @Autowired
    EmailSenderService emailSenderService;

    @EventListener
    public void whenOrderConfirmed(OrderConfirmedEvent event){
        Order order = event.getOrder();
        var message = EmailSenderService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order confirmed")
                .body("orderConfirmed.html")
                .recipient(order.getUser().getEmail())
                .model("order", order)
                .build();
        emailSenderService.send(message);
    }
}
