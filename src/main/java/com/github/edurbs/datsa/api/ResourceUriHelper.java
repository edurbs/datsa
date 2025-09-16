package com.github.edurbs.datsa.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

    // add in the header the link of the new City
    public static void addUriInResponseHeader(Object resourceId){
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceId).toUri();
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if(requestAttributes!=null){
                HttpServletResponse response = ((ServletRequestAttributes)
                    requestAttributes).getResponse();
                if(response!=null){
                    response.setHeader(HttpHeaders.LOCATION, uri.toString());
                }
            }
    }
}
