package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.core.email.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SandBoxEmailService extends SmtpSenderService {

    public SandBoxEmailService(EmailProperties emailProperties, JavaMailSender javaMailSender, EmailProcessorTemplate emailProcessorTemplate) {
        super(emailProperties, javaMailSender, emailProcessorTemplate);
    }

    @Override
    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = super.createMimeMessage(message);
        MimeMessageHelper helper = createMimeMessageHelper(mimeMessage);
        helper.addTo(emailProperties.getSandbox().getRecipient());
        return mimeMessage;
    }

}
