package com.github.edurbs.datsa.infra.email.SmtpEmailService;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setSubject(message.getSubject());
            helper.setText(message.getBody(), true);
            helper.setTo(message.getToList().toArray(new String[0]));
            helper.setFrom(emailProperties.getSender());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new EmailException("Can't send email", e);
        }
    }
}
