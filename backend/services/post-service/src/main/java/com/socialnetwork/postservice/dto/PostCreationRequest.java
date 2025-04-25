package com.socialnetwork.postservice.dto;

import lombok.Data;

@Data
public class PostCreationRequest {
    private String title;
    private String content;
    private String authorId;
}
