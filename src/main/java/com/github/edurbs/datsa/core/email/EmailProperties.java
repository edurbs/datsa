package com.github.edurbs.datsa.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("datsa.mail")
public class EmailProperties {

    @NotNull
    private String sender;

    private String password;

    private Implementation impl = Implementation.FAKE;

    public enum Implementation{
        SMTP, FAKE
    }
}
