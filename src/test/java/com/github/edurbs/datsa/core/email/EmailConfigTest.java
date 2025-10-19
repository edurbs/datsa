package com.github.edurbs.datsa.core.email;

import com.github.edurbs.datsa.domain.service.EmailSenderService;
import com.github.edurbs.datsa.infra.email.FakeEmailService;
import com.github.edurbs.datsa.infra.email.SandBoxEmailService;
import com.github.edurbs.datsa.infra.email.SmtpSenderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class EmailConfigTest {

    EmailProperties emailProperties = new EmailProperties();
    EmailSenderService emailSenderService;

    EmailConfig getEmailConfig(){
        FakeEmailService fakeEmailService = Mockito.mock(FakeEmailService.class);
        SandBoxEmailService sandBoxEmailService = Mockito.mock(SandBoxEmailService.class);
        SmtpSenderService smtpSenderService = Mockito.mock(SmtpSenderService.class);
        return new EmailConfig(emailProperties, smtpSenderService, sandBoxEmailService, fakeEmailService);
    }
    @Test
    void givenEmailProperties_whenImplementationFake_thenReturnsFakeEmailService() {
        // Arrange
        emailProperties.setImpl(EmailProperties.Implementation.FAKE);
        EmailConfig sut = getEmailConfig();

        // Act
        EmailSenderService result = sut.emailSenderService();

        // Assert
        assertThat(result).isInstanceOf(FakeEmailService.class);
    }

    @Test
    void givenEmailProperties_whenImplementationSmtp_thenReturnsSmtpEmailService() {
        // Arrange
        emailProperties.setImpl(EmailProperties.Implementation.SMTP);
        EmailConfig sut = getEmailConfig();

        // Act
        EmailSenderService result = sut.emailSenderService();

        // Assert
        assertThat(result).isInstanceOf(SmtpSenderService.class);
    }

    @Test
    void givenEmailProperties_whenImplementationSandbox_thenReturnsSandboxEmailService() {
        // Arrange
        emailProperties.setImpl(EmailProperties.Implementation.SANDBOX);
        EmailConfig sut = getEmailConfig();

        // Act
        EmailSenderService result = sut.emailSenderService();

        // Assert
        assertThat(result).isInstanceOf(SandBoxEmailService.class);
    }



}