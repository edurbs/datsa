package com.github.edurbs.datsa.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.edurbs.datsa.core.email.EmailProperties.Implementation;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import com.github.edurbs.datsa.infra.email.FakeEmailService;
import com.github.edurbs.datsa.infra.email.SmtpSenderService;

@Configuration
public class EmailConfig {

    @Autowired
    EmailProperties emailProperties;

    @Bean
    public EmailSenderService emailSenderService(){
        if(emailProperties.getImpl().equals(Implementation.FAKE)){
            return new FakeEmailService();
        }
        return new SmtpSenderService();
    }
}
