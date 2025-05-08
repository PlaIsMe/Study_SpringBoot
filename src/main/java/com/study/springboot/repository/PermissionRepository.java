package com.study.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springboot.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}