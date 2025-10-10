package com.github.edurbs.datsa.infra.email;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailService implements EmailSenderService {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private EmailProcessorTemplate emailProcessorTemplate;

    @Override
    public void send(Message message) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(emailProperties.getSender());
            sb.append(message.getRecipients().toArray(new String[0]));
            sb.append(message.getSubject());
            String body = emailProcessorTemplate.processTemplate(message);
            sb.append(body);
            log.info(sb.toString());
        } catch (Exception e ){
            throw new EmailException("Can't write fake email "+e.getMessage(), e);
        }
    }

}
