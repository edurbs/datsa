package com.github.edurbs.datsa.domain.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.GroupNotFoundException;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.model.Group;
import com.github.edurbs.datsa.domain.repository.GroupRepository;

@Service
public class GroupRegistryService {

    @Autowired
    private GroupRepository repository;

    public List<Group> getAll(){
        return repository.findAll();
    }

    public Group getById(Long id){
        return repository.findById(id)
            .orElseThrow(launchNotFoundException(id));
    }

    @Transactional
    public Group save(Group group){
        return repository.save(group);
    }

    private Supplier<GroupNotFoundException> launchNotFoundException(Long id) {
        return () -> new GroupNotFoundException("Group %d does not exists.".formatted(id));
    }

    public void remove(Long id) {
        if(notExists(id)){
            throw new GroupNotFoundException("Group %d does not exists.".formatted(id));
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ModelInUseException("Group id %s is in use".formatted(id));
        }
    }

    private boolean notExists(Long id){
        return !repository.existsById(id);
    }
}
