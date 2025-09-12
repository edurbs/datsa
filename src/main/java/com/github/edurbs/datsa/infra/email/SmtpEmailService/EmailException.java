package com.github.edurbs.datsa.infra.email.SmtpEmailService;

import javax.mail.MessagingException;

public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
