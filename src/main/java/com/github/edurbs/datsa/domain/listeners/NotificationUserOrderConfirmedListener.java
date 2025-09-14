package com.github.edurbs.datsa.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.edurbs.datsa.domain.event.OrderConfirmedEvent;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.service.EmailSenderService;

@Component
public class NotificationUserOrderConfirmedListener {

    @Autowired
    EmailSenderService emailSenderService;

    @TransactionalEventListener
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
