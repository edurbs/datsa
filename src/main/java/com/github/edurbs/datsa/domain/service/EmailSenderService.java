package com.github.edurbs.datsa.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EmailSenderService {
    void send(Message message);

    @Getter
    @Builder
    class Message{
        private Set<String> toList;
        private String subject;
        private String body;
    }
}
