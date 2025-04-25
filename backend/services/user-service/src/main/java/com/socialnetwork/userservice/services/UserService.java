package com.socialnetwork.userservice.services;

import com.socialnetwork.userservice.models.User;

import java.util.Optional;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(String id);

    Optional<User> getUserById(String id);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);
}
