package com.github.edurbs.datsa.core.email;

import com.github.edurbs.datsa.domain.service.EmailSenderService;
import com.github.edurbs.datsa.infra.email.FakeEmailService;
import com.github.edurbs.datsa.infra.email.SandBoxEmailService;
import com.github.edurbs.datsa.infra.email.SmtpSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final SmtpSenderService smtpSenderService;
    private final SandBoxEmailService sandBoxEmailService;
    private final FakeEmailService fakeEmailService;

    @Bean
    public EmailSenderService emailSenderService(){
        return switch (emailProperties.getImpl()) {
            case FAKE -> fakeEmailService;
            case SMTP -> smtpSenderService;
            case SANDBOX -> sandBoxEmailService;
            default -> throw new UnsupportedOperationException("Email implementation not supported");
        };
    }
}
