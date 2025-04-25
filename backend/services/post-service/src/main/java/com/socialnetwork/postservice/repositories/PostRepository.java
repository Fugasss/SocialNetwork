package com.socialnetwork.postservice.repositories;

import com.socialnetwork.postservice.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
