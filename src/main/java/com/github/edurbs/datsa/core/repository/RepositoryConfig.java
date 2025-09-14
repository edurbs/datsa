package com.github.edurbs.datsa.core.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.github.edurbs.datsa.infra.repository.CustomJpaRepositoryImpl;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.github.edurbs.datsa.domain.repository",
    repositoryBaseClass = CustomJpaRepositoryImpl.class
)
public class RepositoryConfig {

}
