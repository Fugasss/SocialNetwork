package com.socialnetwork.postservice.models.mappers;

import com.socialnetwork.postservice.dto.PostCreationRequest;
import com.socialnetwork.postservice.dto.PostDto;
import com.socialnetwork.postservice.models.Post;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {PostMapperUtil.class})
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "authorId", target = "authorId")
    Post createRequestToEntity(PostCreationRequest postCreationRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "authorId", target = "authorName", qualifiedByName = "getAuthorName")
    @Mapping(source = "authorId", target = "authorId")
    @Mapping(source = "createdAt", target = "createdAt")
    PostDto entityToDto(Post post);

    List<PostDto> entityListToDtoList(List<Post> posts);

    default Page<PostDto> pageEntityToDtoPage(Page<Post> posts) {
        return posts.map(this::entityToDto);
    }
}

