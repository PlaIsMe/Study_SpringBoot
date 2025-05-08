package com.study.springboot.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.study.springboot.dto.request.PermissionRequest;
import com.study.springboot.entity.Permission;
import com.study.springboot.mapper.PermissionMapper;
import com.study.springboot.repository.PermissionRepository;
import com.study.springboot.response.PermissionResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasRole('DELETE_DATA')")
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
