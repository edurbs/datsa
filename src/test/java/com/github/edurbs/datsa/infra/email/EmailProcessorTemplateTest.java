package com.github.edurbs.datsa.infra.email;

import com.github.edurbs.datsa.domain.service.EmailSenderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmailProcessosTemplate tests")
class EmailProcessorTemplateTest {

    @Mock
    Configuration freemarkerConfig;

    @Mock
    Template template;

    @Mock
    EmailSenderService.Message message;

    @InjectMocks
    EmailProcessorTemplate emailProcessorTemplate;

    @Test
    @DisplayName("Should process Template")
    void shouldProcessTemplate() throws IOException {

        // arrange
        when(message.getBody()).thenReturn("body");
        when(message.getModels()).thenReturn(Map.of());
        when(freemarkerConfig.getTemplate(any(String.class))).thenReturn(template);
        try (MockedStatic<FreeMarkerTemplateUtils> mockedStatic = mockStatic(FreeMarkerTemplateUtils.class)) {
            mockedStatic.when(() ->
                    FreeMarkerTemplateUtils.processTemplateIntoString(
                            any(Template.class),
                            any(Map.class)
                    )
            ).thenReturn("Processed template");

            // Act & Assert
            assertDoesNotThrow(() -> {
                String processedTemplate = emailProcessorTemplate.processTemplate(message);
                assertEquals("Processed template", processedTemplate);
            });
            verify(message, times(1)).getBody();
            verify(message, times(1)).getModels();
        }
    }

    @Test
    @DisplayName("Should throws Exception When Get Template Fails")
    void shouldThrowsExceptionWhenGetTemplateFails(){
        // Arrange

        // Act & Assert
        EmailException exception = assertThrows(
                EmailException.class,
                () -> emailProcessorTemplate.processTemplate(message));
        assertTrue(exception.getMessage().contains("Can't write email template:"));
    }

    @Test
    @DisplayName("Should throws Exception then process template fails")
    void shouldThrowsExceptionWhenProcessTemplateFails() throws IOException {
        // Arrange
        when(message.getBody()).thenReturn("body");
        when(freemarkerConfig.getTemplate(any(String.class))).thenReturn(template);
        try(MockedStatic<FreeMarkerTemplateUtils> mockedStatic = mockStatic(FreeMarkerTemplateUtils.class)){
            mockedStatic.when(
                    () -> FreeMarkerTemplateUtils.processTemplateIntoString(any(Template.class), any(Map.class))
            ).thenThrow(new RuntimeException());

            // Act & Assert
            EmailException exception = assertThrows(EmailException.class,
                    () -> emailProcessorTemplate.processTemplate(message));
            assertTrue(exception.getMessage().contains("Can't write email template:"));
        }
    }


}