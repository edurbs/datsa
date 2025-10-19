package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SandBoxEmailServiceTest {

    @Mock
    EmailProperties emailProperties;

    @Mock
    EmailProperties.Sandbox sandbox;

    @Mock
    MimeMessageHelper mimeMessageHelper;

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    EmailProcessorTemplate emailProcessorTemplate;

    @Spy
    @InjectMocks
    SandBoxEmailService sut;

    @Test
    void test() throws MessagingException {
        // Arrange
        EmailSenderService.Message message = EmailSenderService.Message.builder()
                .body("body")
                .subject("subject")
                .recipient("test@example.com")
                .build();

        MimeMessage mimeMessage = new MimeMessage((Session) null);
        
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailProcessorTemplate.processTemplate(any())).thenReturn("processed body");
        when(emailProperties.getSender()).thenReturn("sender@example.com");
        when(emailProperties.getSandbox()).thenReturn(sandbox);
        String sandboxEmail = "sandbox@provider.com";
        when(sandbox.getRecipient()).thenReturn(sandboxEmail);
        when(sut.createMimeMessageHelper(any(MimeMessage.class))).thenReturn(mimeMessageHelper);

        // Act
        sut.createMimeMessage(message);

        // Assert
        verify(mimeMessageHelper, times(1)).addTo(sandboxEmail);
    }

}