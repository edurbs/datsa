package com.github.edurbs.datsa.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class MyHalConfiguration {

    @Bean
    public HalConfiguration globalPolicy(){
        return new HalConfiguration()
            .withMediaType(MediaType.APPLICATION_JSON)
            .withMediaType(MyMediaTypes.V1_APPLICATION_JSON);
    }
}
