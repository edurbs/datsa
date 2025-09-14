package com.github.edurbs.datsa.infra.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class SmtpSenderService implements EmailSenderService {

    @Autowired
    protected EmailProperties emailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freemarkerConfig;

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
        String body = processTemplate(message);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getSender());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);
        return mimeMessage;
    }

    protected String processTemplate(Message message){
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getModels());
        } catch (Exception e) {
            throw new EmailException("Can't write email template: "+e.getMessage(), e);
        }

    }
}
