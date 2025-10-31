package com.github.edurbs.datsa.domain.listeners;

import com.github.edurbs.datsa.domain.event.OrderConfirmedEvent;
import com.github.edurbs.datsa.domain.model.Order;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserNotificationOrderConfirmedListener {

    private final EmailSenderService emailSenderService;

    public UserNotificationOrderConfirmedListener(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @TransactionalEventListener
    public void whenOrderConfirmed(OrderConfirmedEvent event){
        Order order = event.getOrder();
        var message = EmailSenderService.Message.builder()
                .subject(order.getRestaurant().getName() + " - Order confirmed")
                .body("emails/orderConfirmed.html")
                .recipient(order.getUser().getEmail())
                .model("order", order)
                .build();
        emailSenderService.send(message);
    }
}
