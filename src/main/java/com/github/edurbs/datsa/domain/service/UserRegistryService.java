package com.github.edurbs.datsa.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.api.dto.output.UserOutput;
import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.UserNotFoundException;
import com.github.edurbs.datsa.domain.model.User;
import com.github.edurbs.datsa.domain.repository.UserRepository;

@Service
public class UserRegistryService {

    @Autowired
    private UserRepository repository;

    public List<User> getAll(){
        return repository.findAll();
    }

    public User getById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void remove(Long id){
        if(notExists(id)){
            throw new UserNotFoundException(id);
        }
        try {
            repository.deleteById(id);
            repository.flush();;
        } catch ( DataIntegrityViolationException e) {
            throw new ModelInUseException("User id %d is in use and can not be removed.".formatted(id));
        }
    }

    @Transactional
    public User save(User user){
        return repository.save(user);
    }

    private boolean notExists(Long id){
        return !repository.existsById(id);
    }

    @Transactional
    public User changePassword(Long id, String oldPassword, String newPassword) {
        var user = getById(id);
        if(user.passwordNotEqualsTo(oldPassword)){
            throw new ModelValidationException("Wrong password");
        }
        user.setPassword(newPassword);
        return repository.save(user);
    }

}
