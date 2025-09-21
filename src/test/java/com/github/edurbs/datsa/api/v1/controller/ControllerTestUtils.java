package com.github.edurbs.datsa.api.v1.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ControllerTestUtils<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Class<T> clazz;

    public ControllerTestUtils(Class<T> clazz){
        this.clazz = clazz;
    }

    public T fromResult(MvcResult result) {
        try {
            String json = result.getResponse().getContentAsString();
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
