package com.github.edurbs.datsa.domain.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.edurbs.datsa.domain.event.OrderCancelledEvent;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.service.EmailSenderService;

@Component
public class UserNotificationOrderCancelledListener {

    @Autowired
    EmailSenderService emailSenderService;

    @TransactionalEventListener
    public void whenOrderCancelled(OrderCancelledEvent event){
        Order order = event.getOrder();
        var message = EmailSenderService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order Cancelled")
                .body("orderCancelled.html")
                .recipient(order.getUser().getEmail())
                .model("order", order)
                .build();
        emailSenderService.send(message);
    }
}
