package com.github.edurbs.datsa.domain.service;

import org.springframework.stereotype.Service;

import com.github.edurbs.datsa.domain.exception.PermissionNotFoundException;
import com.github.edurbs.datsa.domain.model.Permission;
import com.github.edurbs.datsa.infra.repository.PermissionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionRegistryService {

    private PermissionRepository permissionRepository;

    public Permission getOne(Long permissionId){
        return permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }

}
