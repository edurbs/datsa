package com.github.edurbs.datsa.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasAuthority('EDIT_KITCHENS')")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CanEditKitchens {

}
