package com.github.edurbs.datsa.infra.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import com.github.edurbs.datsa.domain.service.EmailSenderService.Message;

@Component
public class EmailProcessorTemplate {

    @Autowired
    private Configuration freemarkerConfig;

    protected String processTemplate(Message message){
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getModels());
        } catch (Exception e) {
            throw new EmailException("Can't write email template: "+e.getMessage(), e);
        }
    }
}
