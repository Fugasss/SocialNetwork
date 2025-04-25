package com.socialnetwork.postservice.services.impl;

import com.socialnetwork.postservice.models.Post;
import com.socialnetwork.postservice.repositories.PostRepository;
import com.socialnetwork.postservice.services.PostService;
import com.socialnetwork.postservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public boolean postCanBeDeletedByUser(Long postId, String userId) {
        Optional<Post> foundPost = postRepository.findById(postId);

        return foundPost
                .map(post -> post.getAuthorId().equals(userId))
                .orElse(false);
    }
}
