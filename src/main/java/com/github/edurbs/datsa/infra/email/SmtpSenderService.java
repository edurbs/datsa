package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpSenderService implements EmailSenderService {

    protected final EmailProperties emailProperties;

    private final JavaMailSender javaMailSender;

    private final EmailProcessorTemplate emailProcessorTemplate;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = createMimeMessage(message);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Can't send email: "+e.getMessage(), e);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String body = emailProcessorTemplate.processTemplate(message);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getSender());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);
        return mimeMessage;
    }

    protected MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage) {
        return new MimeMessageHelper(mimeMessage, "UTF-8");
    }


}
