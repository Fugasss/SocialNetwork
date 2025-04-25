package com.socialnetwork.postservice.controllers;

import com.socialnetwork.postservice.dto.PostDto;
import com.socialnetwork.postservice.models.Post;
import com.socialnetwork.postservice.models.mappers.PostMapper;
import com.socialnetwork.postservice.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public")
public class PublicPostController {

    private final PostService postService;
    private final PostMapper mapper;

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAllPosts(
            @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> allPosts = postService.getAllPosts(pageable);
        Page<PostDto> dto = mapper.pageEntityToDtoPage(allPosts);

        return ResponseEntity.ok(dto);
    }
}
