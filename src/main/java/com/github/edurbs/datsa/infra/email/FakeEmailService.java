package com.github.edurbs.datsa.infra.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEmailService extends SmtpSenderService {

    @Override
    public void send(Message message) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(emailProperties.getSender());
            sb.append(message.getRecipients().toArray(new String[0]));
            sb.append(message.getSubject());
            String body = processTemplate(message);
            sb.append(body);
            log.info(sb.toString());
        } catch (Exception e ){
            throw new EmailException("Can't write fake email "+e.getMessage(), e);
        }
    }

}
