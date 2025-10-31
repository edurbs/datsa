package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.core.email.EmailProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;

import static com.github.edurbs.datsa.domain.service.EmailSenderService.Message;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmtpSenderServiceTest {

    @Mock
    private EmailProperties emailProperties;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailProcessorTemplate emailProcessorTemplate;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @InjectMocks
    @Spy
    private SmtpSenderService sut;

    @BeforeEach
    void cleanMocks(){
        reset(emailProperties, javaMailSender, emailProcessorTemplate, mimeMessage, mimeMessageHelper, sut);
    }

    private Message getMessage(List<String> recipients) {
        return Message.builder()
                .subject("Test Subject")
                .body("Test Body")
                .recipients(recipients)
                .build();
    }

    @Test
    void giveMessage_whenSend_thenSuccess() {
        // Given
        List<String> recipients = List.of("recipient@example.com");
        Message message = getMessage(recipients);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailProcessorTemplate.processTemplate(message)).thenReturn("Processed Body");
        when(sut.createMimeMessageHelper(any(MimeMessage.class))).thenReturn(mimeMessageHelper);
        when(emailProperties.getSender()).thenReturn("sender@example.com");

        // When
        assertDoesNotThrow(() -> sut.send(message));

        // Then
        verify(javaMailSender).createMimeMessage();
        verify(emailProcessorTemplate).processTemplate(message);
        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    void givenMessage_whenJavaMailSenderThrowsException_thenThrowsEmailException()  {
        // Given
        List<String> recipients = List.of("recipient@example.com");
        Message message = getMessage(recipients);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailProcessorTemplate.processTemplate(message)).thenReturn("Processed Body");
        when(sut.createMimeMessageHelper(mimeMessage)).thenReturn(mimeMessageHelper);
        when(emailProperties.getSender()).thenReturn("sender@example.com");
        doThrow(new MailSendException("SMTP error")).when(javaMailSender).send(mimeMessage);

        // When & Then
        Exception exception = assertThrows(EmailException.class, () -> sut.send(message));
        assertTrue(exception.getMessage().contains("SMTP error"));
    }

    @Test
    void givenMessage_whenHelperThrowsMessagingException_thenThrowsEmailException() throws MessagingException {
        // Given
        List<String> recipients = List.of("recipient@example.com");
        Message message = getMessage(recipients);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailProcessorTemplate.processTemplate(message)).thenReturn("Processed Body");
        when(sut.createMimeMessageHelper(mimeMessage)).thenReturn(mimeMessageHelper);
        when(emailProperties.getSender()).thenReturn("sender@example.com");
        doThrow(new MessagingException("Messaging error")).when(mimeMessageHelper).setFrom(any(String.class));

        // When & Then
        Exception exception = assertThrows(EmailException.class, () -> sut.send(message));
        assertTrue(exception.getMessage().contains("Messaging error"));
    }

    @Test
    void givenMessage_whenCreateMimeMessage_thenReturnMimeMessageNotNull() throws MessagingException {
        // Given
        List<String> recipients = List.of("recipient@example.com");
        Message message = getMessage(recipients);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailProcessorTemplate.processTemplate(message)).thenReturn("Processed Body");
        when(emailProperties.getSender()).thenReturn("sender@example.com");

        // When
        MimeMessage result = sut.createMimeMessage(message);

        // Then
        assertNotNull(result);
        verify(javaMailSender).createMimeMessage();
        verify(emailProcessorTemplate).processTemplate(message);
        verify(emailProperties).getSender();
    }

    @Test
    void givenMimeMessage_whenCreateMimeMessageHelper_thenReturnsMimeMessageHelper() {

        // When
        MimeMessageHelper helper = sut.createMimeMessageHelper(mimeMessage);

        // Then
        assertNotNull(helper);
    }

    @Test
    void givenMessageWithMultipleRecipients_whenSend_thenSuccess() throws MessagingException {
        // Given
        List<String> recipients = List.of("recipient1@example.com", "recipient2@example.com");
        Message message = getMessage(recipients);

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(emailProcessorTemplate.processTemplate(message)).thenReturn("Processed Body");
        when(emailProperties.getSender()).thenReturn("sender@example.com");
        when(sut.createMimeMessageHelper(any(MimeMessage.class))).thenReturn(mimeMessageHelper);

        // When
        sut.send(message);

        // Then
        verify(javaMailSender).send(mimeMessage);
        verify(mimeMessageHelper).setTo(recipients.toArray(new String[0]));

    }
}