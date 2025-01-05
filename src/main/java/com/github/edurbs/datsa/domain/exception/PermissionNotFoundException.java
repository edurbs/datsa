package com.github.edurbs.datsa.domain.exception;

public class PermissionNotFoundException extends ModelNotFoundException {

    public PermissionNotFoundException(Long permissionId){
        super("Permission id %d not found.".formatted(permissionId));
    }
}
