package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class FakeEmailService implements EmailSenderService {

    private EmailProperties emailProperties;

    private EmailProcessorTemplate emailProcessorTemplate;

    @Override
    public void send(Message message) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(emailProperties.getSender());
            sb.append(Arrays.toString(message.getRecipients().toArray(new String[0])));
            sb.append(message.getSubject());
            String body = emailProcessorTemplate.processTemplate(message);
            sb.append(body);
            log.info(sb.toString());
        } catch (Exception e ){
            throw new EmailException("Can't write fake email "+e.getMessage(), e);
        }
    }

}
