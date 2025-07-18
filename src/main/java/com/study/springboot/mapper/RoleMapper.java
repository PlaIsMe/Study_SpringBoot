package com.study.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.study.springboot.dto.request.RoleRequest;
import com.study.springboot.dto.response.RoleResponse;
import com.study.springboot.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
