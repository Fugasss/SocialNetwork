package com.socialnetwork.userservice.models.mappers;

import com.socialnetwork.userservice.models.User;
import com.socialnetwork.shared.common.dto.UserCredentialsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    UserCredentialsDto userToUserCredentialsDto(User user);

    User userCredentialsDtoToUser(UserCredentialsDto userCredentialsDto);
}