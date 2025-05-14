package com.study.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

import com.study.springboot.dto.request.UserCreationRequest;
import com.study.springboot.dto.request.UserUpdateRequest;
import com.study.springboot.dto.response.UserResponse;
import com.study.springboot.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}