package com.study.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.study.springboot.dto.request.UserCreationRequest;
import com.study.springboot.dto.request.UserUpdateRequest;
import com.study.springboot.entity.User;
import com.study.springboot.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}