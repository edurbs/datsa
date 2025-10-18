package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.domain.service.EmailSenderService.Message;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
@RequiredArgsConstructor
public class EmailProcessorTemplate {

    private final Configuration freemarkerConfig;

    protected String processTemplate(Message message){
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getModels());
        } catch (Exception e) {
            throw new EmailException("Can't write email template: "+e.getMessage(), e);
        }
    }
}
