package com.github.edurbs.datsa.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.UserNotFoundException;
import com.github.edurbs.datsa.domain.model.Group;
import com.github.edurbs.datsa.domain.model.User;
import com.github.edurbs.datsa.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRegistryService {

    private UserRepository userRepository;
    private GroupRegistryService groupRegistryService;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void remove(Long id){
        if(notExists(id)){
            throw new UserNotFoundException(id);
        }
        try {
            userRepository.deleteById(id);
            userRepository.flush();
        } catch ( DataIntegrityViolationException e) {
            throw new ModelInUseException("User id %d is in use and can not be removed.".formatted(id));
        }
    }

    @Transactional
    public User save(User user){
        // remove the user instance from JPA, so it will not update the database automatically
        userRepository.detach(user);

        Optional<User> userFromDB = userRepository.findByEmail(user.getEmail());
        if(userFromDB.isPresent() && !userFromDB.get().equals(user) ){
            throw new ValidationException("The email %s is already used by another user".formatted(user.getEmail()));
        }
        return userRepository.save(user);
    }

    private boolean notExists(Long id){
        return !userRepository.existsById(id);
    }

    @Transactional
    public User changePassword(Long id, String oldPassword, String newPassword) {
        var user = getById(id);
        if(user.passwordNotEqualsTo(oldPassword)){
            throw new ModelValidationException("Wrong password");
        }
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public Set<Group> getGroups(Long userId) {
        var user = getById(userId);
        return user.getGroups();
    }

    @Transactional
    public void associateGroup(Long userId, Long groupId) {
        var user = getById(userId);
        var group = groupRegistryService.getById(groupId);
        user.addGroup(group);
    }

    @Transactional
    public void dissociateGroup(Long userId, Long groupId){
        var user = getById(userId);
        var group = groupRegistryService.getById(groupId);
        user.removeGroup(group);
    }

}
