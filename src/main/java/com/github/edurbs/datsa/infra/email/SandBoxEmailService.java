package com.github.edurbs.datsa.infra.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.github.edurbs.datsa.core.email.EmailProperties;

public class SandBoxEmailService extends SmtpSenderService {

    @Autowired
    EmailProperties emailProperties;

    @Override
    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = super.createMimeMessage(message);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.addTo(emailProperties.getSandbox().getRecipient());
        return mimeMessage;
    }

}
