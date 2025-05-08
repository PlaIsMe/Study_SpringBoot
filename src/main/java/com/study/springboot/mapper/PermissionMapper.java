package com.study.springboot.mapper;

import org.mapstruct.Mapper;

import com.study.springboot.dto.request.PermissionRequest;
import com.study.springboot.entity.Permission;
import com.study.springboot.response.PermissionResponse;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}