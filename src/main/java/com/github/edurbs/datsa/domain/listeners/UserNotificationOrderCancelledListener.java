package com.github.edurbs.datsa.domain.listeners;

import com.github.edurbs.datsa.domain.event.OrderCancelledEvent;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserNotificationOrderCancelledListener {

    private final EmailSenderService emailSenderService;

    public UserNotificationOrderCancelledListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @TransactionalEventListener
    public void whenOrderCancelled(OrderCancelledEvent event){
        Order order = event.getOrder();
        var message = EmailSenderService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order Cancelled")
                .body("emails/orderCancelled.html")
                .recipient(order.getUser().getEmail())
                .model("order", order)
                .build();
        emailSenderService.send(message);
    }
}
