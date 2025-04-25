package com.socialnetwork.postservice.models.mappers;

import com.socialnetwork.postservice.services.UserService;
import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Named("PostMapperUtil")
@Component
@RequiredArgsConstructor
public class PostMapperUtil{
    private final UserService userService;

    @Named("getAuthorName")
    public String getAuthorName(String authorId){
        ResponseEntity<UserCredentialsDto> response = userService.getUser(authorId);

        if(response.getStatusCode().is4xxClientError() || !response.hasBody()){
            return "Unknown";
        }

        UserCredentialsDto user = response.getBody();

        return user != null ? user.getUsername() : "Unknown";
    }
}
