package com.socialnetwork.shared.web.services;

import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.socialnetwork.shared.common.web.CustomHttpHeaders.X_USER_ID;

public interface UserCredentialsService {

    @GetMapping
    ResponseEntity<UserCredentialsDto> getUser(@RequestHeader(X_USER_ID) String userId);

    @GetMapping("/by-email/{email}")
    ResponseEntity<UserCredentialsDto> getUserByEmail(@PathVariable String email);

    @GetMapping("/by-username/{username}")
    ResponseEntity<UserCredentialsDto> getUserByUsername(@PathVariable String username);

    @PostMapping
    ResponseEntity<UserCredentialsDto> createUser(@RequestBody UserCredentialsDto request);

    @PutMapping
    ResponseEntity<UserCredentialsDto> updateUser(@RequestBody UserCredentialsDto request,
                                                  @RequestHeader(X_USER_ID) String userId);

    @DeleteMapping
    ResponseEntity<Void> deleteUser(@RequestHeader(X_USER_ID) String userId);
}
