package com.socialnetwork.postservice.controllers;

import com.socialnetwork.postservice.dto.PostCreationRequest;
import com.socialnetwork.postservice.dto.PostDto;
import com.socialnetwork.postservice.models.Post;
import com.socialnetwork.postservice.models.mappers.PostMapper;
import com.socialnetwork.postservice.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.socialnetwork.shared.common.web.CustomHttpHeaders.X_USER_ID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper mapper;

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostCreationRequest request,
            @RequestHeader(X_USER_ID) String userId
    ) {
        log.info("Creating post: {}", request);

        request.setAuthorId(userId);

        Post entity = mapper.createRequestToEntity(request);
        Post post = postService.createPost(entity);
        PostDto dto = mapper.entityToDto(post);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestHeader(X_USER_ID) String userId
    ) {
        if(postService.postCanBeDeletedByUser(postId, userId)){
            log.debug("Deleting post with id {}", postId);
            postService.deletePost(postId);
        }else {
            log.debug("Unable to delete post with id {} by user with id {}", postId, userId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }
}
