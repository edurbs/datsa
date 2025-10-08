package com.github.edurbs.datsa.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.edurbs.datsa.domain.exception.ModelInUseException;
import com.github.edurbs.datsa.domain.exception.ModelValidationException;
import com.github.edurbs.datsa.domain.exception.UserNotFoundException;
import com.github.edurbs.datsa.domain.model.Group;
import com.github.edurbs.datsa.domain.model.MyUser;
import com.github.edurbs.datsa.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRegistryService {

    private UserRepository userRepository;
    private GroupRegistryService groupRegistryService;
    private PasswordEncoder passwordEncoder;

    public List<MyUser> getAll(){
        return userRepository.findAll();
    }

    public MyUser getById(Long id){
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
    public MyUser save(MyUser user){
        // remove the user instance from JPA, so it will not update the database automatically
        userRepository.detach(user);

        if(user.isNew()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        Optional<MyUser> userFromDB = userRepository.findByEmail(user.getEmail());
        if(userFromDB.isPresent() && !userFromDB.get().equals(user) ){
            throw new ModelValidationException("The email %s is already used by another user".formatted(user.getEmail()));
        }
        return userRepository.save(user);
    }

    private boolean notExists(Long id){
        return !userRepository.existsById(id);
    }

    @Transactional
    public MyUser changePassword(Long id, String currentPassword, String newPassword) {
        MyUser user = getById(id);
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new ModelValidationException("Current password informed does not matches the user password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
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
