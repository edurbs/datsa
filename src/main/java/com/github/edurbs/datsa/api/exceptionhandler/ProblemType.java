package com.github.edurbs.datsa.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    SYSTEM_ERROR("/system-error", "System error"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
    JSON_ERROR("/json-error", "Json error"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-use", "Entity in use"),
    VALIDATION_ERROR("/validation-error", "Validation error")
    ;

    private String title;
    private String uri;

    ProblemType(String path, String title){
        this.uri = "https://datsa"+path;
        this.title = title;
    }
}
