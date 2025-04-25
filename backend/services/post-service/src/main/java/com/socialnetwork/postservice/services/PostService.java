package com.socialnetwork.postservice.services;

import com.socialnetwork.postservice.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Page<Post> getAllPosts(Pageable pageable);

    Post createPost(Post post);

    void deletePost(Long postId);

    boolean postCanBeDeletedByUser(Long postId, String userId);
}
