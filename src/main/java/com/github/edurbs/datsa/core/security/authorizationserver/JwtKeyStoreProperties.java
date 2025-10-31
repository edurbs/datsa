package com.github.edurbs.datsa.core.security.authorizationserver;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties("datsa.jwt.keystore")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class JwtKeyStoreProperties {

    @NotNull
    Resource jksLocation;

    @NotNull
    String password;

    @NotNull
    String keypairAlias;

}
