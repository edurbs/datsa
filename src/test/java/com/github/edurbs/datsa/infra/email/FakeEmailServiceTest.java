package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.core.email.EmailProperties;
import com.github.edurbs.datsa.domain.service.EmailSenderService;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FakeEmailService tests")
class FakeEmailServiceTest {

    @Mock
    private EmailProperties emailProperties;

    @Mock
    private EmailProcessorTemplate emailProcessorTemplate;

    @InjectMocks
    private FakeEmailService fakeEmailService;

    private EmailSenderService.Message message;

    @BeforeEach
    void setUp() {
        message = EmailSenderService.Message.builder()
                .body("Email body")
                .subject("Email subject")
                .recipient("recipient@example.com")
                .build();
    }

    @Test
    @DisplayName("Log.info should be called")
    void logInfoShouldBeCalled() {
        // Arrange
        when(emailProperties.getSender()).thenReturn("sender@example.com");
        when(emailProcessorTemplate.processTemplate(any(EmailSenderService.Message.class)))
                .thenReturn("processed body");

        // Use try-with-resources to capture log output
        try (LogCaptor logCaptor = LogCaptor.forClass(FakeEmailService.class)) {
            // Act
            fakeEmailService.send(message);

            // Assert
            List<String> infoLogs = logCaptor.getInfoLogs();
            assertFalse(infoLogs.isEmpty(), "Info log should not be empty");

            String expectedSender = "sender@example.com";
            String expectedRecipients = "[recipient@example.com]";
            String expectedSubject = "Email subject";
            String expectedBody = "processed body";
            String expectedLogMessage = expectedSender + expectedRecipients + expectedSubject + expectedBody;

            assertEquals(expectedLogMessage, infoLogs.get(0));
        }
    }

    @Test
    @DisplayName("Should send the email with success")
    void shouldSendEmailWithSuccess() {
        // Arrange
        when(emailProperties.getSender()).thenReturn("from@example.com");
        when(emailProcessorTemplate.processTemplate(any(EmailSenderService.Message.class)))
                .thenReturn("Email body");

        // Act & Assert
        assertDoesNotThrow(() -> fakeEmailService.send(message));

        verify(emailProperties, times(1)).getSender();
        verify(emailProcessorTemplate, times(1)).processTemplate(message);
    }

    @Test
    @DisplayName("Should throws EmailException when processTemplate fails")
    void shouldThrowsEmailExceptionWhenProcessTemplateFails() {
        // Arrange
        when(emailProperties.getSender()).thenReturn("from@example.com");
        when(emailProcessorTemplate.processTemplate(any(EmailSenderService.Message.class)))
                .thenThrow(new RuntimeException("Error processing template"));

        // Act & Assert
        EmailException exception = assertThrows(EmailException.class,
                () -> fakeEmailService.send(message));

        assertTrue(exception.getMessage().contains("Can't write fake email"));
        verify(emailProcessorTemplate, times(1)).processTemplate(message);
    }

    @Test
    @DisplayName("Should send email with multiple recipients")
    void shouldSendEmailWithMultipleRecipients() {
        // Arrange
        EmailSenderService.Message multiRecipients =
                EmailSenderService.Message.builder()
                        .body("Email body")
                        .subject("Email subject")
                        .recipients(Set.of("recipient1@example.com", "recipient2@example.com", "recipient3@example.com"))
                        .build();

        when(emailProperties.getSender()).thenReturn("recipient@example.com");
        when(emailProcessorTemplate.processTemplate(any(EmailSenderService.Message.class)))
                .thenReturn("Processed body");

        // Act & Assert
        assertDoesNotThrow(() -> fakeEmailService.send(multiRecipients));

        verify(emailProperties, times(1)).getSender();
        verify(emailProcessorTemplate, times(1)).processTemplate(multiRecipients);
    }

    @Test
    @DisplayName("Should throws EmailException when emailProperties.getSender() fails")
    void shouldThrowsEmailExceptionWhenGetSenderFails() {
        // Arrange
        when(emailProperties.getSender()).thenThrow(new RuntimeException("Can't get the sender"));

        // Act & Assert
        EmailException exception = assertThrows(EmailException.class,
                () -> fakeEmailService.send(message));

        assertTrue(exception.getMessage().contains("Can't write fake email"));
        verify(emailProperties, times(1)).getSender();
    }
}