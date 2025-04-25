package com.socialnetwork.userservice.controllers;

import com.socialnetwork.userservice.exceptions.UserNotFoundException;
import com.socialnetwork.userservice.exceptions.WrongDataException;
import com.socialnetwork.userservice.models.User;
import com.socialnetwork.userservice.models.mappers.UserMapper;
import com.socialnetwork.userservice.services.UserService;
import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.socialnetwork.shared.common.web.CustomHttpHeaders.X_USER_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserCredentialsController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserCredentialsDto> getUser(@RequestHeader(X_USER_ID) String userId) {
        User user = userService.getUserById(userId).orElseThrow(() -> new UserNotFoundException("User with id %s not found".formatted(userId)));

        if (!user.getId().equals(userId)) {
            throw new WrongDataException("UserID and ID in header do not match!");
        }

        UserCredentialsDto userCredentialsDto = userMapper.userToUserCredentialsDto(user);
        return ResponseEntity.ok(userCredentialsDto);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<UserCredentialsDto> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email %s not found".formatted(email)));

        UserCredentialsDto userCredentialsDto = userMapper.userToUserCredentialsDto(user);
        return ResponseEntity.ok(userCredentialsDto);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserCredentialsDto> getUserByUsername(@PathVariable String username, @RequestHeader(X_USER_ID) String userId) {
        User user = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username %s not found".formatted(username)));

        if (!user.getId().equals(userId)) {
            throw new WrongDataException("UserID and ID in header do not match!");
        }

        UserCredentialsDto userCredentialsDto = userMapper.userToUserCredentialsDto(user);
        return ResponseEntity.ok(userCredentialsDto);
    }

    @PostMapping
    public ResponseEntity<UserCredentialsDto> createUser(@RequestBody @Valid UserCredentialsDto request) {
        User entity = userMapper.userCredentialsDtoToUser(request);

        log.info("Creating user: {}", entity.getUsername());
        entity = userService.createUser(entity);
        log.info("User created: {}, {}", entity.getId(), entity.getUsername());

        UserCredentialsDto dto = userMapper.userToUserCredentialsDto(entity);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<UserCredentialsDto> updateUser(@RequestBody UserCredentialsDto request,
                                                         @RequestHeader(X_USER_ID) String userId) {
        User entity = userService.getUserById(userId).orElseThrow(() -> new UserNotFoundException("User with id %s not found".formatted(userId)));

        if (request.getUsername() != null) entity.setUsername(request.getUsername());
        if (request.getPassword() != null) entity.setPassword(request.getPassword());
        if (request.getEmail() != null) entity.setEmail(request.getEmail());

        log.info("Updating user: {}", entity.getUsername());
        entity = userService.updateUser(entity);
        log.info("User updated: {}, {}", entity.getId(), entity.getUsername());

        UserCredentialsDto dto = userMapper.userToUserCredentialsDto(entity);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestHeader(X_USER_ID) String userId) {
        log.info("Deleting user with id {}", userId);
        userService.deleteUser(userId);
        log.info("User {} deleted", userId);

        return ResponseEntity.ok().build();
    }
}
