package com.socialnetwork.userservice.services.impl;

import com.socialnetwork.userservice.exceptions.UserAlreadyExistsException;
import com.socialnetwork.userservice.models.User;
import com.socialnetwork.userservice.repositories.UserRepository;
import com.socialnetwork.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        boolean userExists = userRepository.existsByEmail(user.getEmail());

        if(userExists) {
            throw new UserAlreadyExistsException("User with email %s already exists".formatted(user.getEmail()));
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
